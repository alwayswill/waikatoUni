android:5.0.1
Android Studio: 1.2

Coding Style:
I followed the android coding style on https://source.android.com/source/code-style.html.

Network:
The myfilmapi website responses slowly on occasion, So I coped with timeout exception and other network exception and set the default sock timeout to 20 seconds.
When user get a timeout toast, they need to reselect the item on navigation bar.

problem solution:

1)
In order to avoid reloading data from api, I saved the moves. When the device is rotated, these movies can be restored. But there is tricky thing here. Because we restore the selection position as well. when we rotate the device as it is requesting, the list should be default status without any selection when the request is finished, because we click one item in navigation bar.

2)
If we try to set a selected item position when the device has been rotated and the stored movies come out. It might crash, I solve this problem in line 118 of MovieListFragment.java.


