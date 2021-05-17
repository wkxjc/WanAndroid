package com.wkxjc.wanandroid.navigation

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.AbsListView
import android.widget.ExpandableListView
import android.widget.FrameLayout

class HeaderPinnedExpandableListView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    var headerView: View? = null
    var headerPinnedExpandableListAdapter: HeaderPinnedExpandableListAdapter? = null
    var expandableListView: ExpandableListView? = null

    init {
        expandableListView = createView()
        addView(expandableListView)
    }

    fun createView(): ExpandableListView {
        val listView = ExpandableListView(context).apply {
            setGroupIndicator(null)
            divider = null
            overScrollMode = View.OVER_SCROLL_NEVER
            isVerticalScrollBarEnabled = false
            setOnScrollListener(object : AbsListView.OnScrollListener {
                var firstVisibleGroupPosition = -1
                override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
                    val position = getGroupPosition(firstVisibleItem)
                    if (firstVisibleGroupPosition != position) {
                        headerPinnedExpandableListAdapter?.onUpdateHeaderView(position, headerView)
                        headerView?.setOnClickListener {
                            if (expandableListView?.isGroupExpanded(position) == true) {
                                expandableListView?.collapseGroup(position)
                            } else {
                                expandableListView?.expandGroup(position)
                            }
                            expandableListView?.smoothScrollToPosition(position)
                        }
                        firstVisibleGroupPosition = position
                    }
                    translateHeaderViewVertically(position)
                }

                override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
                }
            })
        }
        return listView
    }

    private fun translateHeaderViewVertically(firstVisibleGroupPosition: Int) {
        headerView ?: return
        val secondVisibleGroup = getGroupView(expandableListView, firstVisibleGroupPosition + 1)
        if (secondVisibleGroup == null) headerView?.translationY = 0F
        else {
            val top = secondVisibleGroup.top
            val height = headerView!!.height
            if (top < height) {
                headerView!!.translationY = (top - height).toFloat()
            } else {
                headerView!!.translationY = 0F
            }
        }
    }

    private fun getGroupView(expandableListView: ExpandableListView?, groupPosition: Int): View? {
        expandableListView ?: return null
        val position = expandableListView.getFlatListPosition(ExpandableListView.getPackedPositionForGroup(groupPosition))
        return expandableListView.getChildAt(position - expandableListView.firstVisiblePosition)
    }


    fun setAdapter(adapter: HeaderPinnedExpandableListAdapter) {
        expandableListView?.setAdapter(adapter)
        headerView = adapter.getHeaderView(this)
        addView(headerView)
        headerPinnedExpandableListAdapter = adapter
    }

    fun getGroupPosition(rawPosition: Int): Int {
        expandableListView ?: return -1
        return ExpandableListView.getPackedPositionGroup(expandableListView!!.getExpandableListPosition(rawPosition))
    }

}