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
import com.tang.survey.entity.SurveyUserAnswer
import com.tang.survey.service.SurveyUserAnswerService

/**
 * 调查问卷用户答案逻辑控制层
 *
 * @author Tang
 */
@RestController
@RequestMapping("/survey/answer")
class SurveyUserAnswerController(private val surveyUserAnswerService: SurveyUserAnswerService) {

    /**
     * 查询调查问卷用户答案列表
     *
     * @param surveyUserAnswer 调查问卷用户答案对象
     * @return 调查问卷用户答案列表
     */
    @PreAuthorize("@auth.hasPermission('survey:answer:list')")
    @GetMapping("/list")
    fun list(surveyUserAnswer: SurveyUserAnswer): PageResult {
        PageUtils.startPage()
        val list = surveyUserAnswerService.selectSurveyUserAnswerList(surveyUserAnswer)
        return PageUtils.getDataTable(list)
    }

    /**
     * 通过调查问卷用户答案主键查询调查问卷用户答案信息
     *
     * @param answerId 调查问卷用户答案主键
     * @return 调查问卷用户答案信息
     */
    @PreAuthorize("@auth.hasPermission('survey:answer:list')")
    @GetMapping("/{answerId}")
    fun selectSurveyUserAnswerByAnswerId(@PathVariable answerId: Long): AjaxResult {
        val surveyUserAnswer = surveyUserAnswerService.selectSurveyUserAnswerByAnswerId(answerId)
        return AjaxResult.success(surveyUserAnswer)
    }

    /**
     * 新增调查问卷用户答案信息
     *
     * @param surveyUserAnswer 调查问卷用户答案信息
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('survey:answer:add')")
    @PostMapping
    fun insertSurveyUserAnswer(@RequestBody surveyUserAnswer: SurveyUserAnswer): AjaxResult {
        return AjaxResult.rows(surveyUserAnswerService.insertSurveyUserAnswer(surveyUserAnswer))
    }

    /**
     * 通过调查问卷用户答案主键修改调查问卷用户答案信息
     *
     * @param surveyUserAnswer 调查问卷用户答案信息
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('survey:answer:edit')")
    @PutMapping
    fun updateSurveyUserAnswerByAnswerId(@RequestBody surveyUserAnswer: SurveyUserAnswer): AjaxResult {
        return AjaxResult.rows(surveyUserAnswerService.updateSurveyUserAnswerByAnswerId(surveyUserAnswer))
    }

    /**
     * 通过调查问卷用户答案主键删除调查问卷用户答案信息
     *
     * @param answerId 调查问卷用户答案主键
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('survey:answer:delete')")
    @DeleteMapping("/{answerId}")
    fun deleteSurveyUserAnswerByAnswerId(@PathVariable answerId: Long): AjaxResult {
        return AjaxResult.rows(surveyUserAnswerService.deleteSurveyUserAnswerByAnswerId(answerId))
    }

    /**
     * 通过调查问卷用户答案主键数组批量删除调查问卷用户答案信息
     *
     * @param answerIds 调查问卷用户答案主键数组
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('survey:answer:delete')")
    @DeleteMapping
    fun deleteSurveyUserAnswerByAnswerIds(@RequestBody answerIds: Array<Long>): AjaxResult {
        return AjaxResult.rows(surveyUserAnswerService.deleteSurveyUserAnswerByAnswerIds(answerIds))
    }

}
