package com.mogun.domain.model

data class AccountInfo(
    val tokenId: String,
    val name: String,
    val type: Type,
    val profileImageUrl: String
) {
    enum class Type {
        GOOGLE,
        KAKAO,
    }
}
