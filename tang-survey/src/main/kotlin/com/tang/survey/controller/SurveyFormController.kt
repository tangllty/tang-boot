package com.tang.survey.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import com.tang.commons.utils.AjaxResult
import com.tang.commons.utils.page.PageResult
import com.tang.commons.utils.page.PageUtils
import com.tang.survey.entity.SurveyForm
import com.tang.survey.service.SurveyFormService

/**
 * 调查问卷逻辑控制层
 *
 * @author Tang
 */
@RestController
@RequestMapping("/survey/form")
class SurveyFormController(private val surveyFormService: SurveyFormService) {

    /**
     * 查询调查问卷列表
     *
     * @param surveyForm 调查问卷对象
     * @return 调查问卷列表
     */
    @PreAuthorize("@auth.hasPermission('survey:form:list')")
    @GetMapping("/list")
    fun list(surveyForm: SurveyForm): PageResult {
        PageUtils.startPage()
        val list = surveyFormService.selectSurveyFormList(surveyForm)
        return PageUtils.getDataTable(list)
    }

    /**
     * 通过调查问卷主键查询调查问卷信息
     *
     * @param formId 调查问卷主键
     * @return 调查问卷信息
     */
    @PreAuthorize("@auth.hasPermission('survey:form:list')")
    @GetMapping("/{formId}")
    fun selectSurveyFormByFormId(@PathVariable formId: Long): AjaxResult {
        val surveyForm = surveyFormService.selectSurveyFormByFormId(formId)
        return AjaxResult.success(surveyForm)
    }

    /**
     * 新增调查问卷信息
     *
     * @param surveyForm 调查问卷信息
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('survey:form:add')")
    @PostMapping
    fun insertSurveyForm(@RequestBody surveyForm: SurveyForm): AjaxResult {
        return AjaxResult.rows(surveyFormService.insertSurveyForm(surveyForm))
    }

    /**
     * 通过调查问卷主键修改调查问卷信息
     *
     * @param surveyForm 调查问卷信息
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('survey:form:edit')")
    @PutMapping
    fun updateSurveyFormByFormId(@RequestBody surveyForm: SurveyForm): AjaxResult {
        return AjaxResult.rows(surveyFormService.updateSurveyFormByFormId(surveyForm))
    }

    /**
     * 通过调查问卷主键删除调查问卷信息
     *
     * @param formId 调查问卷主键
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('survey:form:delete')")
    @DeleteMapping("/{formId}")
    fun deleteSurveyFormByFormId(@PathVariable formId: Long): AjaxResult {
        return AjaxResult.rows(surveyFormService.deleteSurveyFormByFormId(formId))
    }

    /**
     * 通过调查问卷主键数组批量删除调查问卷信息
     *
     * @param formIds 调查问卷主键数组
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('survey:form:delete')")
    @DeleteMapping
    fun deleteSurveyFormByFormIds(@RequestBody formIds: Array<Long>): AjaxResult {
        return AjaxResult.rows(surveyFormService.deleteSurveyFormByFormIds(formIds))
    }

}
