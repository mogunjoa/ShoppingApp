package com.mogun.presentation.model

import com.mogun.domain.model.BannerList
import com.mogun.presentation.deligate.BannerDelegate

class BannerListVM(model: BannerList, private val bannerDelegate: BannerDelegate) : PresentationVM<BannerList>(model) {
    fun openBannerList(bannerId: String) {
        bannerDelegate.openBanner(bannerId)
    }
}