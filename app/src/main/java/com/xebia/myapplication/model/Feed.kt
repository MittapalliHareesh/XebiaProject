package com.xebia.myapplication.model

import android.os.Parcelable
import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import kotlinx.android.parcel.Parcelize

/**
 * data classes for xml response parsing
 * */

@Xml(name = "feed")
data class Feed(
    @PropertyElement(name = "id") val id: String?,
    @PropertyElement(name = "title") val title: String?,
    @Element(name = "entry") val entryList: List<Entry>?
)

@Parcelize
@Xml(name = "entry")
data class Entry(
    @PropertyElement(name = "title") val title: String?,
    @PropertyElement(name = "id") val id: String?,
    @PropertyElement(name = "published") val publishedDt: String?,
    @PropertyElement(name = "updated") val updatedDt : String?,
    @Element val link: Link,
    @Element val authorDetails: Author
) : Parcelable


@Xml(name = "link")
@Parcelize
data class Link(
    @Attribute(name = "type") val type: String?,
    @Attribute(name = "href") val imageLink: String?
) : Parcelable


@Xml(name = "author")
@Parcelize
data class Author(
    @PropertyElement(name = "name") val authorName: String?
) : Parcelable


data class ProgressBarStatus(
    val status: Boolean,
    val message: String
)