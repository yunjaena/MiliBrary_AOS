package dev.yunzai.milibrary.constant

const val STAGE_SERVER_BASE_URL = "http://13.124.139.11/"
const val PRODUCTION_SERVER_BASE_URL = "http://13.124.139.11/"

const val ACCESS_TOKEN = "ACCESS_TOKEN"
const val REFRESH_TOKEN = "REFRESH_TOKEN"
const val USER_ID = "USER_ID"
const val USER_NICKNAME = "USER_NICKNAME"

const val STATUS_OK = "OK"

/* koin */
const val URL = "URL"
const val NO_AUTH = "NO_AUTH"
const val AUTH = "AUTH"
const val REFRESH_AUTH = "REFRESH_AUTH"
const val LOCAL_REPOSITORY = "LOCAL_REPOSITORY"
const val REMOTE_REPOSITORY = "REMOTE_REPOSITORY"

const val EXTRA_BOOK_ID = "EXTRA_BOOK_ID"
const val EXTRA_EDIT_MODE = "EXTRA_EDIT_MODE"
const val EXTRA_BOOK_MARK_ID = "EXTRA_BOOK_MARK_ID"
const val EXTRA_BOOK_LIST_SORT_TYPE = "EXTRA_BOOK_LIST_SORT_TYPE"

const val SORT_TYPE_DATE = "date"
const val SORT_TYPE_SCORE = "score"

const val SORT_TYPE_BOOK_YEAR_ASC_QRT_DESC = "year:asc,qtr:desc" // => 연도 먼저 오름차순하고 분기로 내림차순
const val SORT_TYPE_BOOK_YEAR_ASC_QRT_ASC = "qtr:asc,year:desc" // => 분기 먼저 오름차순하고 연도로 내림차순
const val SORT_TYPE_BOOK_YEAR_DESC_QRT_DESC = "year:desc,qtr:desc" // => 최신 목록
const val SORT_TYPE_BOOK_YEAR_DESC = "year:desc" // => 연도만 내림차순
const val SORT_TYPE_BOOK_QRT_ASC = "qtr:asc" // => 연도만 오름차순 (편집됨)
