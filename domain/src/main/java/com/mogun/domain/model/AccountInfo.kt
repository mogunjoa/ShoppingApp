package com.mogun.domain.model

data class AccountInfo(
    val tokenId: String,
    val name: String,
    val type: Type
) {
    enum class Type {
        GOOGLE,
        KAKAO,
    }
}
