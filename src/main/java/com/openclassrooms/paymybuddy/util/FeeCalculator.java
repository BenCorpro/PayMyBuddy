package com.openclassrooms.paymybuddy.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.openclassrooms.paymybuddy.constants.Constants;

public class FeeCalculator {

  public static BigDecimal feeCalculator(BigDecimal amount) {
    BigDecimal fee = amount.multiply(Constants.feePercent).setScale(2, RoundingMode.HALF_DOWN);
    return fee;
  }
  
}
