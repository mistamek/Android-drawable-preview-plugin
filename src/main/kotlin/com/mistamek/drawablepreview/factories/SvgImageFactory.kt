package com.mistamek.drawablepreview.factories

import com.mistamek.drawablepreview.settings.SettingsUtils
import org.apache.batik.transcoder.TranscoderInput
import org.apache.batik.transcoder.TranscoderOutput
import org.apache.batik.transcoder.image.PNGTranscoder
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import javax.imageio.ImageIO

object SvgImageFactory {

    fun createSvgImage(
        path: String
    ): BufferedImage? {
        val transcoder = PNGTranscoder()
        transcoder.addTranscodingHint(PNGTranscoder.KEY_WIDTH, SettingsUtils.getPreviewSize().toFloat())
        transcoder.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, SettingsUtils.getPreviewSize().toFloat())

        try {
            val inputStream = FileInputStream(File(path))
            val input = TranscoderInput(inputStream)

            val outputStream = ByteArrayOutputStream()
            val output = TranscoderOutput(outputStream)

            transcoder.transcode(input, output)

            outputStream.flush()
            outputStream.close()

            val imgData = outputStream.toByteArray()
            return ImageIO.read(ByteArrayInputStream(imgData))
        } catch (exception: Exception) {
            return null
        }
    }
}