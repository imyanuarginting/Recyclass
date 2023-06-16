package com.example.recyclass.data.pagingforarticles

import androidx.paging.PagingState
import com.example.recyclass.data.dataclass.Article
import com.example.recyclass.data.retrofit.ApiService

class PagingSource(private val apiService: ApiService, private val plastic_type: String) : androidx.paging.PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        val anchorPage = state.closestPageToPosition(state.anchorPosition ?: FIRST_INDEX)
        return anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val position = params.key ?: FIRST_INDEX
            val data = apiService.getArticles(plastic_type, position, params.loadSize).result
            LoadResult.Page(
                data = data,
                prevKey = if (position == FIRST_INDEX) null else position - 1,
                nextKey = if (data.isEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    companion object {
        const val FIRST_INDEX = 1
    }
}