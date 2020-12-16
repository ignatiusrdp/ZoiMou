package id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.opengl

import android.content.Context
import android.graphics.PixelFormat
import android.opengl.GLSurfaceView

class MyGLSurfaceView(context: Context) : GLSurfaceView(context) {

    private val renderer: MyGLRenderer

    init {

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2)
        preserveEGLContextOnPause = true
        renderer = MyGLRenderer()
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer)
    }

}