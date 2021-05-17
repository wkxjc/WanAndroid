package com.wkxjc.wanandroid.home.common.bean

enum class KnowledgeTreeType(val intValue: Int) {
    GROUP(0),
    CHILD(1);

//    fun getIntValue(): Int {
//        return intValue
//    }
}

object DisplayedKnowledgeTreeBeanFactory {
    fun generateParentFromKnowledgeTreeBean(bean: KnowledgeTreeBean): DisplayedKnowledgeTreeBean {
        return DisplayedKnowledgeTreeBean(bean.name, bean.id, KnowledgeTreeType.GROUP)
    }

    fun generateChildrenFromKnowledgeTree(bean: KnowledgeTreeBean): MutableList<DisplayedKnowledgeTreeBean> {
        return mutableListOf<DisplayedKnowledgeTreeBean>().apply {
            bean.children.forEach {
                add(DisplayedKnowledgeTreeBean(it.name, it.id, KnowledgeTreeType.CHILD))
            }
        }
    }
}