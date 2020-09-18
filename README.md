# StickyScrollView

StickyScrollView is a scroll view in which columns or rows(TBD) are fixed.

We are planning to add more kinds of scroll views.

## StickyColumnHorizontalScrollView

StickyColumnHorizontalScrollView is a horizontal scroll view that sticks the first column in the recyclerView.

The sticky column is fixed to the left and you can make the width of the sticky column appear to be adjusted when scrolling. Of course, you can fix the width of the column. It also supports when the header is outside the recyclerView.

### Screenshots

TODO

### How to use

1. Add the following gradle dependency.

	```kotlin
	TODO
	```

### Usage

For more details, please see the [sample code](StickyScrollView/sample).

#### Warning

The sticky column must be a below format to work as intended. 😢

```xml
<Layout>
	<TextView />
	<!-- If the paddingLeft is the same, you can add more TextView -->
</Layout>
```

#### Usage 1

The width of the sticky column is not adjusted as you scroll.

```xml
<com.spoqa.stickyscrollview.StickyColumnHorizontalScrollView
	android:id="@+id/main_scrollView"
	android:layout_width="wrap_content"
	android:layout_height="wrap_content"
	android:scrollbars="horizontal">

	<androidx.recyclerview.widget.RecyclerView
		android:id="@+id/main_recyclerView"
		android:layout_width="wrap_content"
		android:layout_height="wrap_content"
		app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager" />

</com.spoqa.stickyscrollview.StickyColumnHorizontalScrollView>
```

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
	...
	main_scrollView.run {
		recyclerView = main_recyclerView
	}
}
```

#### Usage 2

The width of the sticky column is adjusted as you scroll.

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
	...
	main_scrollView.run {
		recyclerView = main_recyclerView
		minWidthOfStickyColumn = (100 * context.resources.displayMetrics.density).toInt()
	}
}
```

#### Usage 3

The header is outside the Recycleview.

```xml
<com.spoqa.stickyscrollview.StickyColumnHorizontalScrollView
	android:id="@+id/main_scrollView"
	android:layout_width="wrap_content"
	android:layout_height="wrap_content"
	android:scrollbars="horizontal">

	<LinearLayout
		android:layout_width="wrap_content"
		android:layout_height="wrap_content"
		android:orientation="vertical">

		<LinearLayout
			android:layout_width="wrap_content"
			android:layout_height="wrap_content"
			android:orientation="horizontal">

			<LinearLayout
				android:id="@+id/main_stickyHeaderColumnLayout"
				android:layout_width="wrap_content"
				android:layout_height="wrap_content"
				android:orientation="horizontal">

				<TextView
					android:layout_width="150dp"
					android:layout_height="wrap_content"
					android:text="sticky header" />

			</LinearLayout>

			<TextView
				android:layout_width="100dp"
				android:layout_height="wrap_content"
				android:text="header 1" />

		</LinearLayout>

	</LinearLayout>

	<androidx.recyclerview.widget.RecyclerView
		android:id="@+id/main_recyclerView"
		android:layout_width="wrap_content"
		android:layout_height="wrap_content"
		app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager" />

</com.spoqa.stickyscrollview.StickyColumnHorizontalScrollView>
```

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
	...
	main_scrollView.run {
		stickyHeaderColumn = main_stickyHeaderColumnLayout
		recyclerView = main_recyclerView
		minWidthOfStickyColumn = (100 * context.resources.displayMetrics.density).toInt()
	}
}
```

## License

For more details, please see the [LICENSE](StickyScrollView/LICENSE).

```
Copyright 2020 Spoqa
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
