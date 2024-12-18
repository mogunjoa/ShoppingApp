package com.mogun.presentation.ui.main

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.mogun.domain.model.AccountInfo
import com.mogun.presentation.viewmodel.MainViewModel

@Composable
fun MyPageScreen(
    viewModel: MainViewModel,
    googleSignInClient: GoogleSignInClient,
    navHostController: NavHostController
) {
    val accountInfo by viewModel.accountInfo.collectAsState()
    val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    val context = LocalContext.current
    val startForResult =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data

                if (intent != null) {
                    val task: Task<GoogleSignInAccount> =
                        GoogleSignIn.getSignedInAccountFromIntent(intent)
                    handleSignInResult(
                        context = context,
                        accountTask = task,
                        viewModel = viewModel,
                        firebaseAuth = firebaseAuth
                    )
                }
            }
        }

    val kakaoCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        when {
            error != null -> {
                Log.e("Kakao", "카카오계정으로 로그인 실패", error)
            }

            token != null -> {
                loginWithKakaoNickName(token, viewModel)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (accountInfo != null) {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = accountInfo?.profileImageUrl)
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                        }).build()
                ),
                contentDescription = "profileImage",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .padding(5.dp)
                    .clip(CircleShape),
                alignment = Alignment.Center
            )
            Text(
                text = accountInfo?.name.orEmpty(),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            Spacer(modifier = Modifier.height(50.dp))
            Button(
                onClick = {
                    viewModel.openPurchaseHistory(navHostController)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    text = "결제내역 보기",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Icon(Icons.Filled.ArrowForward, contentDescription = "")
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    viewModel.signOut()
                    when (accountInfo?.type) {
                        AccountInfo.Type.KAKAO -> {
                            UserApiClient.instance.logout { }
                        }

                        AccountInfo.Type.GOOGLE -> {
                            firebaseAuth.signOut()
                        }

                        null -> {}
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(text = "로그아웃")
            }
            Spacer(modifier = Modifier.height(70.dp))
        } else {
            Button(
                onClick = {
                    startForResult.launch(googleSignInClient.signInIntent)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "로그인")
            }

            Button(
                onClick = {
                    loginKakao(context, kakaoCallback)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "카카오 로그인")
            }
        }
    }
}

private fun loginWithKakaoNickName(token: OAuthToken, viewModel: MainViewModel) {
    UserApiClient.instance.me { user, error ->
        when {
            error != null -> {
                Log.e("Kakao", "사용자 정보 요청 실패", error)
            }

            user != null -> {
                val imageUrl = user.kakaoAccount?.profile?.thumbnailImageUrl
                val nickName = user.kakaoAccount?.profile?.nickname

                viewModel.signIn(
                    AccountInfo(
                        tokenId = token.accessToken,
                        name = nickName.orEmpty(),
                        type = AccountInfo.Type.KAKAO,
                        profileImageUrl = imageUrl.orEmpty()
                    )
                )
            }
        }
    }
}

private fun loginKakao(context: Context, kakaoCallback: (OAuthToken?, Throwable?) -> Unit) {
    if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
        // 카카오톡 설치 되어 있는 경우
        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
            if (error != null) {
                Log.e("Kakao", "카카오톡 로그인 실패", error)
            }

            if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                return@loginWithKakaoTalk
            }

            UserApiClient.instance.loginWithKakaoAccount(context, callback = kakaoCallback)
        }
    } else {
        // 카카오톡 미설치
        UserApiClient.instance.loginWithKakaoAccount(context, callback = kakaoCallback)
    }
}

private fun handleSignInResult(
    context: Context,
    accountTask: Task<GoogleSignInAccount>,
    viewModel: MainViewModel,
    firebaseAuth: FirebaseAuth
) {
    try {
        val account = accountTask.result ?: return
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(context as Activity) { task ->
                if (task.isSuccessful) {
                    viewModel.signIn(
                        AccountInfo(
                            account.idToken.orEmpty(),
                            account.displayName.orEmpty(),
                            AccountInfo.Type.GOOGLE,
                            account.photoUrl.toString()
                        )
                    )
                } else {
                    viewModel.signOut()
                    firebaseAuth.signOut()
                }
            }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}