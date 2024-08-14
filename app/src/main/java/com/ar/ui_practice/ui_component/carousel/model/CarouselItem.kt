package com.ar.ui_practice.ui_component.carousel.model

import androidx.annotation.DrawableRes

data class CarouselItem constructor(
    val imageUrl: String? = null,
    @DrawableRes val imageDrawable: Int? = null,
    val caption: String? = null,
    val subtitle: String? = null,
    val headers: Map<String, String>? = null,
) {
    constructor() : this(null, null, null, null, null)

    constructor(imageUrl: String? = null) : this(
        imageUrl,
        null,
        null,
        null,
        null,
    )

    constructor(imageUrl: String? = null, headers: Map<String, String>? = null) : this(
        imageUrl,
        null,
        null,
        null,
        headers,
    )

    constructor(
        @DrawableRes imageDrawable: Int? = null,
    ) : this(
        null,
        imageDrawable,
        null,
        null,
        null,
    )

    constructor(imageUrl: String? = null, caption: String? = null) : this(
        imageUrl,
        null,
        caption,
        null,
        null,
    )

    constructor(
        imageUrl: String? = null,
        caption: String? = null,
        headers: Map<String, String>? = null,
    ) : this(
        imageUrl,
        null,
        caption,
        null,
        headers,
    )

    constructor(
        @DrawableRes imageDrawable: Int? = null,
        caption: String? = null,
    ) : this(
        null,
        imageDrawable,
        caption,
        null,
        null,
    )

    constructor(
        imageUrl: String? = null,
        @DrawableRes imageDrawable: Int? = null,
    ) : this(
        imageUrl,
        imageDrawable,
        null,
        null,
        null,
    )

    constructor(
        imageUrl: String? = null,
        @DrawableRes imageDrawable: Int? = null,
        caption: String? = null,
    ) : this(
        imageUrl,
        imageDrawable,
        caption,
        null,
        null,
    )

    constructor(
        imageUrl: String? = null,
        @DrawableRes imageDrawable: Int? = null,
        caption: String? = null,
        subtitle: String? = null,
    ) : this(
        imageUrl,
        imageDrawable,
        caption,
        subtitle,
        null,
    )

    constructor(
        @DrawableRes imageDrawable: Int? = null,
        caption: String? = null,
        subtitle: String? = null,
    ) : this(
        null,
        imageDrawable,
        caption,
        subtitle,
        null,
    )

    constructor(
        @DrawableRes imageDrawable: Int? = null,
        caption: String? = null,
        subtitle: String? = null,
        headers: Map<String, String>? = null,
    ) : this(
        null,
        imageDrawable,
        caption,
        subtitle,
        headers,
    )
}
