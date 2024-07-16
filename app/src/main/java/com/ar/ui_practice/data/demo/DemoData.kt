package com.ar.ui_practice.data.demo

import com.ar.ui_practice.R.drawable.*
import com.ar.ui_practice.data.model.Service
import com.ar.ui_practice.data.model.Services
import com.ar.ui_practice.data.model.ShortcutData

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
}