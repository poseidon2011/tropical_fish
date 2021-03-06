package com.mcoding.modular.generatecode.strategy;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.mcoding.common.exception.CommonException;
import com.mcoding.modular.generatecode.entity.BaseGenerateCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class DateIncrementStrategy extends GenerateStrategy {

    private String dateFormatter = "yyyyMMdd";

    @Override
    public String generateNextCode(BaseGenerateCode generateCode) {
        String currentCode = generateCode.getCurrentCode();
        String maxCode = generateCode.getMaxCode();
        if (StringUtils.isBlank(maxCode)) {
            maxCode = "99999";
        }

        int maxLength = maxCode.length();
        String currentDateStr = DateUtil.format(new Date(), "yyyyMMdd");
        if (StringUtils.isBlank(currentCode)) {
            return currentDateStr + StringUtils.leftPad("1", maxLength, "0");
        }

        // 日期前缀不存在
        if (StrUtil.indexOf(currentCode, currentDateStr, 0, false) != 0) {
            return currentDateStr + StringUtils.leftPad("1", maxLength, "0");
        }

        String currentIncrementStr = currentCode.replaceFirst(currentDateStr, "");
        BigDecimal incrementStr = new BigDecimal(currentIncrementStr).add(BigDecimal.ONE);

        BigDecimal maxCodeBd = new BigDecimal(maxCode);
        if (maxCodeBd.compareTo(incrementStr) < 0) {
            throw new CommonException("流水号已经到了最大值，无法生成流水号了");
        }

        return currentDateStr + StringUtils.leftPad(incrementStr.toString(), maxLength, "0");


    }

}
