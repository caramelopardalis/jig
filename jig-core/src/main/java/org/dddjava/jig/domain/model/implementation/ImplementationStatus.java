package org.dddjava.jig.domain.model.implementation;

import org.dddjava.jig.domain.model.implementation.datasource.SqlReadStatus;

public enum ImplementationStatus {
    テキストソースなし("implementation.TextSourceNotFound"),
    バイナリソースなし("implementation.BinarySourceNotFound"),
    SQLなし("implementation.SqlNotFound"),
    SQL読み込み一部失敗("implementation.SqlReadWarning"),
    SQL読み込み失敗("implementation.SqlReadError");

    String messageKey;

    ImplementationStatus(String messageKey) {
        this.messageKey = messageKey;
    }

    public static ImplementationStatus fromSqlReadStatus(SqlReadStatus sqlReadStatus) {
        switch (sqlReadStatus) {
            case SQLなし:
                return SQLなし;
            case 読み取り失敗あり:
                return SQL読み込み一部失敗;
            case 失敗:
                return SQL読み込み失敗;
        }
        throw new IllegalArgumentException(sqlReadStatus.toString());
    }

    public boolean isError() {
        return this == バイナリソースなし;
    }
}