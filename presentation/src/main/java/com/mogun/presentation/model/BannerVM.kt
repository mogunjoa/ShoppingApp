package com.mogun.presentation.model

import com.mogun.domain.model.Banner
import com.mogun.presentation.deligate.BannerDelegate

class BannerVM(model: Banner, bannerDelegate: BannerDelegate) : PresentationVM<Banner>(model),
    BannerDelegate by bannerDelegate {
}