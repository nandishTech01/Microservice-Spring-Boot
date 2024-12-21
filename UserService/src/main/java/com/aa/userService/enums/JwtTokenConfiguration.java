package com.aa.userservice.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public enum JwtTokenConfiguration {

    AUTH_PUBLIC_KEY("""
            -----BEGIN PUBLIC KEY-----
            MIGeMA0GCSqGSIb3DQEBAQUAA4GMADCBiAKBgGMYi2V/gB6TsrwozWTgMnrD2noU
            Y9ZPK4QOkpC/01reUZfv8tEr85b0/aSmN61oyvCsnv/gILveI8E+sZcVtw0PxQf8
            O/aU8cVbldwZPURopz3UqHfTSJfFURT7iTFkJjJgwC7i08PmSzL+eOfYxQETwlh2
            HSf5Znf1YL8tO09BAgMBAAE=
            -----END PUBLIC KEY-----
            """),
    AUTH_PRIVATE_KEY("""
            -----BEGIN RSA PRIVATE KEY-----
            MIICWwIBAAKBgGMYi2V/gB6TsrwozWTgMnrD2noUY9ZPK4QOkpC/01reUZfv8tEr
            85b0/aSmN61oyvCsnv/gILveI8E+sZcVtw0PxQf8O/aU8cVbldwZPURopz3UqHfT
            SJfFURT7iTFkJjJgwC7i08PmSzL+eOfYxQETwlh2HSf5Znf1YL8tO09BAgMBAAEC
            gYAW9YjWa0Pz0aSHk6yCwq7vYpEr162J6a3bWsT6ZHdDhl7BJQbND7HQN9LMYvZj
            ZvQd7uCnx/XNkRoXm77pkk5mnm5B0uNCMKfiDdZzF0lf2YQRrsUWZe4j3IOkfkXu
            yLBvfM1lkwmWJu+zTuEXM7BMLgW+lzeD58tD6N9kbWwnAQJBAKbchRBKp7pWt69B
            /cOf5xXq4Cd2vAnOyVzyNmyOQ7cYhjOXN5MoOqC3WRGewmFVKcqyQdaugp26bzav
            kYxavbUCQQCYCJu4VU1wa8zVuhdKnT6Fp2fhJLKzAmiHiTRPJEA+vFZwpuceUY1P
            MK1wa3F1JwI0h2DbfDdPAeO35HpLFaLdAkBmILKQtiDSvCDQ8+MNyOD3WTq47MNK
            fXZhnR4Sc0Ce4DPDf9pUB1ta4t1xG2p9iFJ77X6+lzD6uRE4t1yuOxvBAkEAjKG5
            2YMehNVSvojKrrxRbzRxRLO7kRazNlxWRLbVrrJsivv4YfUx74Spion4g1O4GHr5
            v01ho/WtfRcdgILdCQJAOr0jSXGuEtIA4NKAQMTxsQtC8zpuhHuRTxSb1nB+w178
            cTOoF71C91oDkpSNnOW4t6i/m2LtKRBFQCaYuwom2w==
            -----END RSA PRIVATE KEY-----
            """);

    private final String defaultValue;

    JwtTokenConfiguration(String defaultValue){
        this.defaultValue = defaultValue;
    }

    public String getDefaultValue() {
        return defaultValue;
    }
}
