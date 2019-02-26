/*
 * Copyright (c) 2018 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.android.rwandroidtutorial

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.*


/**
 * Main Screen
 */
class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val edtFileName = findViewById<EditText>(R.id.edtFileName)
    val edtNoteText = findViewById<EditText>(R.id.edtNoteText)

    val btnRead = findViewById<Button>(R.id.btnRead)
    val btnWrite = findViewById<Button>(R.id.btnWrite)
    val btnDelete = findViewById<Button>(R.id.btnDelete)




    btnWrite.setOnClickListener(View.OnClickListener {
      val fileName : String = edtFileName.text.toString()

      if(fileName.isNotEmpty()) {
        var externalFile: File = File(getExternalFilesDir(null), fileName)

        try {
          val fileOutputStream = FileOutputStream(externalFile)
          val noteText: String = edtNoteText.text.toString()
          fileOutputStream.write(noteText.toByteArray())
          fileOutputStream.close()
        } catch (e: IOException) {
          e.printStackTrace()
        }
      }
      else {
        Toast.makeText(applicationContext,"Please provide a Filename", Toast.LENGTH_LONG).show()
      }
    })

    btnRead.setOnClickListener(View.OnClickListener {
      val fileName : String = edtFileName.text.toString()

      if(fileName.isNotEmpty()) {
        var externalFile = File(getExternalFilesDir(null), fileName)
          try {
            var fileInputStream = FileInputStream(externalFile)
            var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
            val stringBuilder: StringBuilder = StringBuilder()
            var text: String? = null
            while ({ text = bufferedReader.readLine(); text }() != null) {
              stringBuilder.append(text + "\n")
            }
            fileInputStream.close()
            edtNoteText.setText(stringBuilder.toString()).toString()
          } catch (e: IOException) {
            e.printStackTrace()
          }
      }
      else {
        Toast.makeText(applicationContext,"Please provide a Filename",Toast.LENGTH_LONG).show()
      }
    })

    btnDelete.setOnClickListener(View.OnClickListener {
      val fileName : String = edtFileName.text.toString()
      var externalFile: File = File(getExternalFilesDir(null), fileName)
      try {
        externalFile.delete()
        edtFileName.text.clear()
        edtNoteText.text.clear()

      } catch (e: IOException) {
        e.printStackTrace()
      }

    })
  }
}
