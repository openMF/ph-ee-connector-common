/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.mifos.connector.common.ams.dto;

public enum SavingsAccountSubStatusEnum {

    NONE(0, "SavingsAccountSubStatusEnum.none"), //
    INACTIVE(100, "SavingsAccountSubStatusEnum.inactive"), //
    DORMANT(200, "SavingsAccountSubStatusEnum.dormant"), ESCHEAT(300, "SavingsAccountSubStatusEnum.escheat"), BLOCK(400,
            "SavingsAccountSubStatusEnum.block"), BLOCK_CREDIT(500,
                    "SavingsAccountSubStatusEnum.blockCredit"), BLOCK_DEBIT(600, "SavingsAccountSubStatusEnum.blockDebit");

    private final Integer value;
    private final String code;

    SavingsAccountSubStatusEnum(final Integer value, final String code) {
        this.value = value;
        this.code = code;
    }

    public Integer getValue() {
        return this.value;
    }

    public String getCode() {
        return this.code;
    }
}
