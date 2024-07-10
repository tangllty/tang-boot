package com.tang.survey.entity

import java.time.LocalDate
import java.time.LocalDateTime

import org.apache.commons.lang3.StringUtils

import com.tang.commons.base.entity.BaseEntity

/**
 * 调查问卷用户答案实体类 qs_survey_user_answer
 *
 * @author Tang
 */
class SurveyUserAnswer : BaseEntity() {

    companion object {
        @java.io.Serial
        private val serialVersionUID = 1L
    }

    /**
     * 答案ID
     */
    var answerId: Long? = null

    /**
     * 问卷ID
     */
    var formId: Long? = null

    /**
     * 答案数据
     */
    var answerData: String? = null

    /**
     * 答题耗时(毫秒)
     */
    var answerTime: Long? = null

    /**
     * IP地址
     */
    var ip: String? = null

    /**
     * 地点
     */
    var location: String? = null

    /**
     * 是否为手机
     */
    var mobile: String? = null

    /**
     * 浏览器
     */
    var browser: String? = null

    /**
     * 版本
     */
    var version: String? = null

    /**
     * 平台
     */
    var platform: String? = null

    /**
     * 操作系统
     */
    var os: String? = null

    /**
     * 系统版本
     */
    var osVersion: String? = null

    /**
     * 引擎
     */
    var engine: String? = null

    /**
     * 引擎版本
     */
    var engineVersion: String? = null

}
