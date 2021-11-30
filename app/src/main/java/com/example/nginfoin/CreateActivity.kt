package com.example.nginfoin

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.example.nginfoin.databinding.ActivityCreateBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class CreateActivity : AppCompatActivity() {

    private lateinit var databaseRef : DatabaseReference
    private lateinit var binding: ActivityCreateBinding
    private lateinit var currentUser : FirebaseUser
    private lateinit var mAuth: FirebaseAuth
    private lateinit var listArticle : ArrayList<Article>
    private lateinit var btn_create : Button
    private lateinit var btn_back : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = Firebase.auth
        supportActionBar?.hide()

        binding.etTitleCreate.addTextChangedListener(textWatcher)
        binding.textInputCreate.addTextChangedListener(textWatcher)

        try {
            currentUser = mAuth.currentUser!!
            println("==============================${currentUser.uid}=========================")
        } catch (e: Exception){
            Log.d(TAG, "User not logged in")
        }
        listArticle = arrayListOf()
        val include_create = findViewById<View>(R.id.include_appbar_create)
        btn_create = include_create.findViewById<Button>(R.id.button_create)
        btn_create.setOnClickListener {
            Toast.makeText(this, "Artikel telah dibuat", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, ListActivity::class.java))
        }
        btn_back = findViewById<ImageView>(R.id.back_create)
        btn_back.setOnClickListener {
            startActivity(Intent(this, ListActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        getListArticle()
        binding.includeAppbarCreate.buttonCreate.setOnClickListener {
            val newTitle = binding.etTitleCreate.text.toString()
            val newContent = binding.textInputCreate.text.toString()
            createArticle(newTitle, newContent)
        }
    }

    private fun createArticle(title: String, content: String){
        databaseRef = Firebase.database.getReference("Article")
        val newId = getLastId()
        val newArticle = Article(newId, title, content, currentUser.uid)
        databaseRef.child(newId.toString()).setValue(newArticle).addOnSuccessListener {
            Toast.makeText(this, "Article created", Toast.LENGTH_SHORT).show()
            goHome()
        }
    }

    private fun getListArticle(){
        databaseRef = FirebaseDatabase.getInstance().getReference("Article")
        databaseRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(articleSnapshot in snapshot.children){
                        val article = articleSnapshot.getValue<Article>()
                        listArticle.add(article!!)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun goHome() {
        val home = Intent(this, ListActivity::class.java)
        startActivity(home)
        finish()
    }

    private fun getLastId() : Int{
        var lastId = listArticle.size
        for (item in listArticle){
            lastId = item.idArticle
        }
        return (lastId + 1)
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val titleInput = binding.etTitleCreate.text.toString().trim()
            val contentInput = binding.textInputCreate.text.toString()

            if (titleInput.isEmpty() || contentInput.isEmpty()){
                btn_create.isEnabled = false
            }
        }

        override fun afterTextChanged(p0: Editable?) {
            val titleInput = binding.etTitleCreate.text.toString().trim()
            val contentInput = binding.textInputCreate.text.toString()

            if (!titleInput.isEmpty() && !contentInput.isEmpty()){
                btn_create.isEnabled = true
            }
        }
    }
}