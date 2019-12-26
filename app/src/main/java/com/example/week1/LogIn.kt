package com.example.week1

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.screen_login.*
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.week1.R
import com.example.week1.Adapter.NoticeBoardAdapter
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.example.week1.Model.NoticeBoardItem
import com.example.week1.Post

class LogIn : AppCompatActivity(), View.OnClickListener, NoticeBoardAdapter.clickListener, NavigationView.OnNavigationItemSelectedListener {

    override fun go_nextpage(data: NoticeBoardItem) {
        val open_post_intent = Intent(this, Post::class.java)
        open_post_intent.putExtra("content_title", data.title)
        open_post_intent.putExtra("content_writer", data.writer)
        open_post_intent.putExtra("content_time", data.time)
        open_post_intent.putExtra("content_content", data.content)
        startActivity(open_post_intent)
    }

    //fab (버튼)에 사용할 변수
    private var isfabopen = false
    private lateinit var fab_open:Animation
    private lateinit var fab_close:Animation

    //게시판에 들어갈 데이터들
    private val NB_TempData = ArrayList<NoticeBoardItem>()

    //게시판에 사용할 recyclerview
    private lateinit var NB_RecyclerViewAdapter: NoticeBoardAdapter<LogIn>
    private lateinit var NB_RecyclerView : RecyclerView

    //navigation view에 사용할 변수
    private var drawerToggle: ActionBarDrawerToggle? = null


    private fun logout(){
        var pref_edit = getSharedPreferences("auto_login_saved_pref", Context.MODE_PRIVATE).edit()
        pref_edit.putBoolean("auto_login", false)
        pref_edit.commit()
        startActivity(Intent(this, MainActivity::class.java))
        finish()

        //이렇게 intent로 내용 실어서 보내도 됨, 하지만 위의 것으로 data 저장해 나가는 게 좋겠지?
        //val logout_intent = Intent(this, MainActivity::class.java)
        //logout_intent.putExtra("logout", true)
        //startActivity(Intent(this, MainActivity::class.java))
        //finish()
    }


    private fun fab_animation(){
        if(isfabopen == false){
            fab_logout.startAnimation(fab_open)
            fab_logout.isClickable = true
            fab_search.startAnimation(fab_open)
            fab_search.isClickable = true
            fab_create.startAnimation(fab_open)
            fab_create.isClickable = true
            isfabopen = true
        }
        else{
            fab_logout.startAnimation(fab_close)
            fab_logout.isClickable = false
            fab_search.startAnimation(fab_close)
            fab_search.isClickable = false
            fab_create.startAnimation(fab_close)
            fab_create.isClickable = false
            isfabopen = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_login)

        //툴바
        setSupportActionBar(login_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val entering_ID = intent.getStringExtra("login_passid")
        val entering_PW = intent.getStringExtra("login_passpw")

        fab_open = AnimationUtils.loadAnimation(this, R.anim.fab_open)
        fab_close = AnimationUtils.loadAnimation(this, R.anim.fab_close)

        fab_add.setOnClickListener(this)
        fab_search.setOnClickListener(this)
        fab_create.setOnClickListener(this)
        fab_logout.setOnClickListener(this)
        //logout button
        /*
        logout_button.setOnClickListener {
            logout()
        }
        fab_add.setOnClickListener{
            fab_animation()
        }
        fab_create.setOnClickListener{
            Toast.makeText(this, "Create!", Toast.LENGTH_SHORT).show()
        }
        fab_search.setOnClickListener{
            Toast.makeText(this, "Search!", Toast.LENGTH_SHORT).show()
        }
        fab_logout.setOnClickListener{
            logout()
        }*/


        //data 추가
        NB_TempData.add(NoticeBoardItem("스크롤체크", "체크", "원투쓰리",
            "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
                    "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n악\n\n\n\n\n\n"))
        NB_TempData.add(NoticeBoardItem("제목1", "작성자1", "시간1", "내용1"))
        NB_TempData.add(NoticeBoardItem("제목2", "작성자2", "시간2", "내용2"))
        NB_TempData.add(NoticeBoardItem("제목3", "작성자3", "시간3", "내용3"))
        NB_TempData.add(NoticeBoardItem("제목4", "작성자4", "시간4", "내용4"))

        //
        NB_RecyclerView = findViewById<RecyclerView>(R.id.NoticeBoardRecyclerView)

        //adapter 생성/지정
        NB_RecyclerViewAdapter = NoticeBoardAdapter(this)
        NB_RecyclerView.adapter = NB_RecyclerViewAdapter

        //layout manager 지정
        NB_RecyclerView.layoutManager = LinearLayoutManager(this)


        NB_RecyclerViewAdapter.setData(NB_TempData)


        var idx = 5
        //갱신
        NoticeBoardRefresh.setOnRefreshListener {
            NB_TempData.add(NoticeBoardItem("제목"+idx, "작성자"+idx, "시간"+idx, "내용"+idx))
            NB_RecyclerViewAdapter.setData(NB_TempData)
            idx = idx + 1
            NoticeBoardRefresh.isRefreshing = false
        }

        //navigation view
        drawerToggle = ActionBarDrawerToggle(this, drawer_layout_logint, login_toolbar, 0, 0)
        drawer_layout_logint.addDrawerListener(drawerToggle!!)
        drawerToggle!!.syncState()

        login_navigation_view.setNavigationItemSelectedListener(this)
    }

    override fun onCreateOptionsMenu(menu:Menu):Boolean{
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    //option의 item이 클릭되었을 때(action bar에 있는 메뉴)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_filter->{
                Toast.makeText(applicationContext, "filter", Toast.LENGTH_SHORT).show()
                return true;
            }
            R.id.action_info->{
                Toast.makeText(applicationContext, "version 1.0.0", Toast.LENGTH_SHORT).show()
                return true;
            }
            R.id.action_setting->{
                Toast.makeText(applicationContext, "setting", Toast.LENGTH_SHORT).show()
                return true;
            }
            else->{
                return super.onOptionsItemSelected(item)
            }
        }
    }

    //fab 버튼이 클릭되었을 때
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.fab_add->{
                fab_animation()
            }
            R.id.fab_create->{
                Toast.makeText(this, "Create!", Toast.LENGTH_SHORT).show()
            }
            R.id.fab_logout->{
                logout()
            }
            R.id.fab_search->{
                Toast.makeText(this, "Search!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //navigation view의 item이 클릭되었을 때
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.navi_bookmark->Toast.makeText(this, "북마크 여는중", Toast.LENGTH_SHORT).show()
            R.id.navi_feedback->Toast.makeText(this, "피드백 감사합니다.", Toast.LENGTH_SHORT).show()
            R.id.navi_setting->Toast.makeText(this, "설정 여는중", Toast.LENGTH_SHORT).show()
            R.id.navi_tag->Toast.makeText(this, "당신의 태그", Toast.LENGTH_SHORT).show()
            R.id.navi_wrotten->Toast.makeText(this, "작성한 글", Toast.LENGTH_SHORT).show()
        }
        drawer_layout_logint.closeDrawer(GravityCompat.START)
        return false
    }

    //뒤로가기 버튼이 열렸을 때 : navigation view가 열렸을 때는 뒤로가기 누르면 navigation view만 닫아야 함.
    override fun onBackPressed() {
        if(drawer_layout_logint.isDrawerOpen(GravityCompat.START)){
            drawer_layout_logint.closeDrawer(GravityCompat.START)
        }
        else{
            super.onBackPressed()
        }
    }

}
