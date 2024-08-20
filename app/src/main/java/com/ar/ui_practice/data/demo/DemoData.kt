package com.ar.ui_practice.data.demo

import com.ar.ui_practice.R.drawable.*
import com.ar.ui_practice.data.model.MobileOperator
import com.ar.ui_practice.data.model.Service
import com.ar.ui_practice.data.model.Services
import com.ar.ui_practice.data.model.ShortcutData
import com.ar.ui_practice.data.model.top_up.Offer
import com.ar.ui_practice.data.model.top_up.OfferItem
import com.ar.ui_practice.data.model.top_up.Offers
import com.ar.ui_practice.ui_component.carousel.model.CarouselItem

object DemoData {
    val shortcutData = listOf(
        ShortcutData(0, ic_send_money, "Send Money"),
        ShortcutData(1, ic_add_money_card, "Add Money"),
        ShortcutData(2, ic_education, "Education"),
        ShortcutData(3, ic_remittance, "Remittance"),
        ShortcutData(4, ic_request_money, "Request"),
        ShortcutData(5, ic_transfer, "Transfer"),
        ShortcutData(6, ic_card, "Cards"),
        ShortcutData(7, ic_iternet, "Internet"),
        ShortcutData(8, ic_wasa, "WASA"),
        ShortcutData(9, ic_electricity, "Electricity"),
        ShortcutData(10, ic_cable_tv, "Cable TV"),
        ShortcutData(11, ic_gas, "Gas"),
        ShortcutData(12, ic_news, "News"),
        ShortcutData(13, ic_ewpd, "EWPD"),
        ShortcutData(14, ic_govt_payment, "Govt. Payment"),
    )

    val MoneyOut = listOf(
        Service(0, ic_send_money, "Send Money"),
        Service(1, ic_recharge, "Recharge"),
        Service(2, ic_payment, "Payment"),
        Service(3, ic_transfer, "Transfer"),
        Service(4, ic_cashout, "Cash Out"),
    )

    val MoneyIn = listOf(
        Service(0, ic_add_money_card, "Add Money"),
        Service(1, ic_request_money, "Request"),
    )

    val Others = listOf(
        Service(0, ic_education, "Education"),
        Service(1, ic_remittance, "Remittance"),
        Service(2, ic_card, "Pocket Cards"),
        Service(3, ic_iternet, "Internet"),
        Service(4, ic_govt_payment, "Govt. Payment"),
        Service(5, ic_electricity, "Electricity"),
        Service(6, ic_cable_tv, "Cable TV"),
        Service(7, ic_gas, "Gas"),
    )


    val operatorList = listOf(
        MobileOperator(
            id = 0,
            title = "Grameen Phone",
            icon = ic_gp
        ),
        MobileOperator(
            id = 1,
            title = "Airtel",
            icon = ic_ar
        ),
        MobileOperator(
            id = 2,
            title = "Robi",
            icon = ic_rb
        ),
        MobileOperator(
            id = 3,
            title = "Banglalink",
            icon = ic_bl
        ),
        MobileOperator(
            id = 0,
            title = "Teletok",
            icon = ic_tltc
        ),
        MobileOperator(
            id = 0,
            title = "Skitto",
            icon = ic_skitto
        )
    )


    val ServiceCategories = listOf(
        Services(
            id = 0,
            title = "Money Out -",
            MoneyOut
        ),
        Services(
            id = 1,
            title = "Money In +",
            MoneyIn
        ),
        Services(
            id = 2,
            title = "Others",
            Others
        )
    )

    private val internetOfferList = listOf(
        Offer(
            expire = "30 Days",
            price = "500",
            bundle = listOf(
                OfferItem(
                    amount = "50 GB",
                    icon = ic_web
                )
            )
        ),
        Offer(
            expire = "30 Days",
            price = "300",
            bundle = listOf(
                OfferItem(
                    amount = "30 GB",
                    icon = ic_web
                )
            )
        ),
        Offer(
            expire = "15 Days",
            price = "150",
            bundle = listOf(
                OfferItem(
                    amount = "15 GB",
                    icon = ic_web
                )
            )
        ),
        Offer(
            expire = "7 Days",
            price = "100",
            bundle = listOf(
                OfferItem(
                    amount = "7 GB",
                    icon = ic_web
                )
            )
        ),
        Offer(
            expire = "3 Days",
            price = "89",
            bundle = listOf(
                OfferItem(
                    amount = "3 GB",
                    icon = ic_web
                )
            )
        )
    )

    private val minuteOfferList = listOf(
        Offer(
            expire = "30 Days",
            price = "500",
            bundle = listOf(
                OfferItem(
                    amount = "500 Min",
                    icon = ic_phone
                )
            )
        ),
        Offer(
            expire = "30 Days",
            price = "500",
            bundle = listOf(
                OfferItem(
                    amount = "500 Min",
                    icon = ic_phone
                )
            )
        ),
        Offer(
            expire = "30 Days",
            price = "300",
            bundle = listOf(
                OfferItem(
                    amount = "300 Min",
                    icon = ic_phone
                )
            )
        ),
        Offer(
            expire = "15 Days",
            price = "199",
            bundle = listOf(
                OfferItem(
                    amount = "200 Min",
                    icon = ic_phone
                )
            )
        ),
        Offer(
            expire = "3 Days",
            price = "70",
            bundle = listOf(
                OfferItem(
                    amount = "50 Min",
                    icon = ic_phone
                )
            )
        )
    )

    private val myOfferList = listOf(
        Offer(
            expire = "30 Days",
            price = "500",
            bundle = listOf(
                OfferItem(
                    amount = "500 GB",
                    icon = ic_web
                )
            )
        ),
        Offer(
            expire = "30 Days",
            price = "500",
            bundle = listOf(
                OfferItem(
                    amount = "500 Min",
                    icon = ic_phone
                )
            )
        ),
        Offer(
            expire = "30 Days",
            price = "100",
            bundle = listOf(
                OfferItem(
                    amount = "300 SMS",
                    icon = ic_sms
                )
            )
        ),
        Offer(
            expire = "15 Days",
            price = "199",
            bundle = listOf(
                OfferItem(
                    amount = "20 GB",
                    icon = ic_web
                )
            )
        ),
        Offer(
            expire = "7 Days",
            price = "129",
            bundle = listOf(
                OfferItem(
                    amount = "10 GB",
                    icon = ic_web
                )
            )
        ),
        Offer(
            expire = "15 Days",
            price = "199",
            bundle = listOf(
                OfferItem(
                    amount = "5 GB",
                    icon = ic_web
                ),
                OfferItem(
                    amount = "10 Min",
                    icon = ic_phone
                ),
                OfferItem(
                    amount = "100 SMS",
                    icon = ic_sms
                )
            )
        )
    )

    private val callRateOfferList = listOf(
        Offer(
            expire = "60 Days",
            price = "399",
            bundle = listOf(
                OfferItem(
                    amount = "69 Poisa/Min",
                    icon = ic_phone
                )
            )
        ),
        Offer(
            expire = "30 Days",
            price = "299",
            bundle = listOf(
                OfferItem(
                    amount = "69 Poisa/Min",
                    icon = ic_phone
                )
            )
        ),
        Offer(
            expire = "15 Days",
            price = "99",
            bundle = listOf(
                OfferItem(
                    amount = "69 Poisa/Min",
                    icon = ic_phone
                )
            )
        )
    )
    private val bundleOfferList = listOf(
        Offer(
            expire = "30 Days",
            price = "599",
            bundle = listOf(
                OfferItem(
                    amount = "40 GB",
                    icon = ic_web
                ),
                OfferItem(
                    amount = "100 Min",
                    icon = ic_phone
                ),
                OfferItem(
                    amount = "150 SMS",
                    icon = ic_sms
                )
            )
        ),
        Offer(
            expire = "15 Days",
            price = "199",
            bundle = listOf(
                OfferItem(
                    amount = "5 GB",
                    icon = ic_web
                ),
                OfferItem(
                    amount = "10 Min",
                    icon = ic_phone
                ),
                OfferItem(
                    amount = "100 SMS",
                    icon = ic_sms
                )
            )
        ),
        Offer(
            expire = "7 Days",
            price = "129",
            bundle = listOf(
                OfferItem(
                    amount = "3 GB",
                    icon = ic_web
                ),
                OfferItem(
                    amount = "100 Min",
                    icon = ic_phone
                ),
                OfferItem(
                    amount = "50 SMS",
                    icon = ic_sms
                )
            )
        ),
        Offer(
            expire = "3 Days",
            price = "99",
            bundle = listOf(
                OfferItem(
                    amount = "3 GB",
                    icon = ic_web
                ),
                OfferItem(
                    amount = "50 Min",
                    icon = ic_phone
                ),
                OfferItem(
                    amount = "50 SMS",
                    icon = ic_sms
                )
            )
        )
    )

    val OffersList = listOf(
        Offers(
            category = "Internet",
            offerList = internetOfferList
        ),
        Offers(
            category = "My Offer",
            offerList = myOfferList
        ),
        Offers(
            category = "Call Rate",
            offerList = callRateOfferList
        ),
        Offers(
            category = "Minute",
            offerList = minuteOfferList
        ),
        Offers(
            category = "Bundle",
            offerList = bundleOfferList
        )
    )

    val promotionList = listOf(
        CarouselItem(
            imageUrl = "https://ecdn.dhakatribune.net/contents/cache/images/1100x618x1/uploads/dten/2022/06/20/free-talk-time-sylhet.jpeg",
            caption = "Celebrating Bangladesh's 50th Independence Day with Pride \uD83C\uDDE7\uD83C\uDDE9",
            subtitle = "This vibrant graphic marks the 50th anniversary of Bangladesh's independence. The design features bold typography and the iconic red and green colors of the national flag, symbolizing the nation's enduring spirit and resilience. The message, '50 Years of Freedom,' highlights the journey of progress and unity since 1971."
        ),
        CarouselItem(
            imageUrl = "https://coorzjdvba.cloudimg.io/markedium.com/wp-content/uploads/2021/11/Copy-of-Add-a-subheading-1080-x-1350-px51.png",
            caption = "Exclusive GP Star Discount on Truck Hiring with Truck Lagbe \uD83D\uDE9A",
            subtitle = "This promotional image showcases a special offer for GP Star customers, providing discounts on truck hiring services through Truck Lagbe. The visual highlights the convenience and affordability of transporting goods across the country, featuring the Truck Lagbe logo alongside the GP Star branding."
        ),
        CarouselItem(
            imageUrl = "https://blog.trucklagbe.com/hubfs/GP%20Star%20Discount%20on%20truck%20hiring%20from%20Truck%20Lagbe.jpg",
            caption = "Stay Connected with GP's Exclusive Mobile Plans \uD83D\uDCF1",
            subtitle = "This image highlights Grameenphone's latest mobile plans, designed to keep users connected with high-speed data and affordable rates. The sleek and modern design features a smartphone alongside the Grameenphone logo, symbolizing the brand's commitment to providing cutting-edge technology and exceptional service."
        ),
        CarouselItem(
            imageUrl = "https://blog.trucklagbe.com/hubfs/GP%20Star%20Discount%20on%20truck%20hiring%20from%20Truck%20Lagbe.jpg",
            caption = "Stay Connected with GP's Exclusive Mobile Plans \uD83D\uDCF1",
            subtitle = "This image highlights Grameenphone's latest mobile plans, designed to keep users connected with high-speed data and affordable rates. The sleek and modern design features a smartphone alongside the Grameenphone logo, symbolizing the brand's commitment to providing cutting-edge technology and exceptional service."
        ),
    )
}