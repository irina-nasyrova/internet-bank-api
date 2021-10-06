package com.bank.internetbankapi.domain.constants;


public enum OperationTypeEnum {
    //снятие со счета,
    TAKE_MONEY,

    // пополнение счета
    PUT_MONEY,

    // перевод другому клиенту
    TRANSFER_TO,

    // перевод от другого клиента вам
    TRANSFER_FROM
}
