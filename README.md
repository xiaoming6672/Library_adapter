# Library_adapter
Adapter library

To get a Git project into your build:

Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.xiaoming6672:Library_adapter:xxx'
	}

如果使用SuperRecyclerAdapter，必须在混淆文件中添加混淆声明

	     -keep class * extends com.zhang.library.adapter.viewholder.base.SuperViewHolder {*;}

v1.0.0
    添加通用的ListView、RecyclerView的适配器

v1.0.1
    添加能适配多种ViewHolder的RecyclerView的适配器

v1.0.2
    BaseRecyclerAdapter添加Header、Footer功能
