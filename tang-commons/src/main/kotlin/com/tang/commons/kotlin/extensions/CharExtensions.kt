package com.tang.commons.kotlin.extensions

/**
 * @author Tang
 */

/**
 * Half-width: Regular width characters.
 * E.g. 'A' and 'ﾆ'
 *
 * Full-width: Chars that take two monospaced English chars' space on the display
 * E.g. '中', 'に' and 'Ａ'
 *
 * See https://stackoverflow.com/questions/62805375/kotlin-java-how-to-identify-full-width-characters
 *
 * @return Is this character a full-width character or not.
 */
fun Char.isFullWidth(): Boolean {
    return when (this) {
        '\u2329','\u232A','\u23F0','\u23F3','\u267F','\u2693','\u26A1','\u26CE','\u26D4','\u26EA','\u26F5',
        '\u26FA','\u26FD','\u2705','\u2728','\u274C','\u274E','\u2757','\u27B0','\u27BF','\u2B50','\u2B55',
        '\u3000','\u3004','\u3005','\u3006','\u3007','\u3008','\u3009','\u300A','\u300B','\u300C','\u300D',
        '\u300E','\u300F','\u3010','\u3011','\u3014','\u3015','\u3016','\u3017','\u3018','\u3019','\u301A',
        '\u301B','\u301C','\u301D','\u3020','\u3030','\u303B','\u303C','\u303D','\u303E','\u309F','\u30A0',
        '\u30FB','\u30FF','\u3250','\uA015','\uFE17','\uFE18','\uFE19','\uFE30','\uFE35','\uFE36','\uFE37',
        '\uFE38','\uFE39','\uFE3A','\uFE3B','\uFE3C','\uFE3D','\uFE3E','\uFE3F','\uFE40','\uFE41','\uFE42',
        '\uFE43','\uFE44','\uFE47','\uFE48','\uFE58','\uFE59','\uFE5A','\uFE5B','\uFE5C','\uFE5D','\uFE5E',
        '\uFE62','\uFE63','\uFE68','\uFE69','\uFF04','\uFF08','\uFF09','\uFF0A','\uFF0B','\uFF0C','\uFF0D',
        '\uFF3B','\uFF3C','\uFF3D','\uFF3E','\uFF3F','\uFF40','\uFF5B','\uFF5C','\uFF5D','\uFF5E','\uFF5F',
        '\uFF60','\uFFE2','\uFFE3','\uFFE4',
        in '\u1100'..'\u115F',in '\u231A'..'\u231B',in '\u23E9'..'\u23EC',in '\u25FD'..'\u25FE',
        in '\u2614'..'\u2615',in '\u2648'..'\u2653',in '\u26AA'..'\u26AB',in '\u26BD'..'\u26BE',
        in '\u26C4'..'\u26C5',in '\u26F2'..'\u26F3',in '\u270A'..'\u270B',in '\u2753'..'\u2755',
        in '\u2795'..'\u2797',in '\u2B1B'..'\u2B1C',in '\u2E80'..'\u2E99',in '\u2E9B'..'\u2EF3',
        in '\u2F00'..'\u2FD5',in '\u2FF0'..'\u2FFB',in '\u3001'..'\u3003',in '\u3012'..'\u3013',
        in '\u301E'..'\u301F',in '\u3021'..'\u3029',in '\u302A'..'\u302D',in '\u302E'..'\u302F',
        in '\u3031'..'\u3035',in '\u3036'..'\u3037',in '\u3038'..'\u303A',in '\u3041'..'\u3096',
        in '\u3099'..'\u309A',in '\u309B'..'\u309C',in '\u309D'..'\u309E',in '\u30A1'..'\u30FA',
        in '\u30FC'..'\u30FE',in '\u3105'..'\u312F',in '\u3131'..'\u318E',in '\u3190'..'\u3191',
        in '\u3192'..'\u3195',in '\u3196'..'\u319F',in '\u31A0'..'\u31BF',in '\u31C0'..'\u31E3',
        in '\u31F0'..'\u31FF',in '\u3200'..'\u321E',in '\u3220'..'\u3229',in '\u322A'..'\u3247',
        in '\u3251'..'\u325F',in '\u3260'..'\u327F',in '\u3280'..'\u3289',in '\u328A'..'\u32B0',
        in '\u32B1'..'\u32BF',in '\u32C0'..'\u32FF',in '\u3300'..'\u33FF',in '\u3400'..'\u4DBF',
        in '\u4E00'..'\u9FFC',in '\u9FFD'..'\u9FFF',in '\uA000'..'\uA014',in '\uA016'..'\uA48C',
        in '\uA490'..'\uA4C6',in '\uA960'..'\uA97C',in '\uAC00'..'\uD7A3',in '\uF900'..'\uFA6D',
        in '\uFA6E'..'\uFA6F',in '\uFA70'..'\uFAD9',in '\uFADA'..'\uFAFF',in '\uFE10'..'\uFE16',
        in '\uFE31'..'\uFE32',in '\uFE33'..'\uFE34',in '\uFE45'..'\uFE46',in '\uFE49'..'\uFE4C',
        in '\uFE4D'..'\uFE4F',in '\uFE50'..'\uFE52',in '\uFE54'..'\uFE57',in '\uFE5F'..'\uFE61',
        in '\uFE64'..'\uFE66',in '\uFE6A'..'\uFE6B',in '\uFF01'..'\uFF03',in '\uFF05'..'\uFF07',
        in '\uFF0E'..'\uFF0F',in '\uFF10'..'\uFF19',in '\uFF1A'..'\uFF1B',in '\uFF1C'..'\uFF1E',
        in '\uFF1F'..'\uFF20',in '\uFF21'..'\uFF3A',in '\uFF41'..'\uFF5A',in '\uFFE0'..'\uFFE1',
        in '\uFFE5'..'\uFFE6' -> true
        else -> false
    }
}
