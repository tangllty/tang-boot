package com.tang.survey.entity

import java.time.LocalDate
import java.time.LocalDateTime

import org.apache.commons.lang3.StringUtils

import com.tang.commons.base.entity.BaseEntity

/**
 * 调查问卷实体类 qs_survey_form
 *
 * @author Tang
 */
class SurveyForm : BaseEntity() {

    companion object {
        @java.io.Serial
        private val serialVersionUID = 1L
    }

    /**
     * 问卷ID
     */
    var formId: Long? = null

    /**
     * 问卷编码
     */
    var formCode: Long? = null

    /**
     * 问卷名称
     */
    var formName: String? = null

    /**
     * 问卷数据
     */
    var formData: String? = null

    /**
     * 发布状态{0=未发布, 1=已发布}
     */
    var publishStatus: String? = null

    /**
     * 发布时间
     */
    var publishTime: LocalDateTime? = null

    /**
     * 关闭时间
     */
    var closeTime: LocalDateTime? = null

    /**
     * 状态{0=正常, 1=停用}
     */
    var status: String? = null

}
