<template>
  <div>
    <el-menu
        :default-active="this.$store.state.menus.editableTabsValue"
        class="el-menu-vertical-demo"
        unique-opened="unique-opened"
        @open="handleOpen"
        @close="handleClose"
        background-color="#545c64"
        text-color="#fff"
        active-text-color="#ffd04b">
      <router-link to="/index">
        <el-menu-item index="Index" @click="addTab({name:'Index',title:'首页'})">
          <template slot="title">
            <i class="el-icon-s-home"></i>
            <span>首页</span>
          </template>
        </el-menu-item>
      </router-link>
      <el-submenu :index="menu.name" :key="index" v-for="(menu,index) in menuList">
        <template slot="title">
          <i :class="menu.icon"></i>
          <span>{{ menu.title }}</span>
        </template>
        <router-link :to="item.path" :key="index" v-for="(item,index) in menu.children">
          <el-menu-item :index="item.name" @click="addTab(item)">
            <template slot="title">
              <i :class="item.icon"></i>
              <span>{{ item.title }}</span>
            </template>
          </el-menu-item>
        </router-link>
      </el-submenu>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: "SideMenu",
  methods: {
    handleOpen(key, keyPath) {

    },
    handleClose(key, keyPath) {

    },
    addTab(item) {
      this.$store.commit("addTab", item)
    },
    modifyMenuList() {
      this.$axios.get("/sys/userInfo").then(res => {
        const typeId = res.data.data.typeId
        if (typeId === 0) {
          this.menuList = [
            {
              name: 'JobWanted',
              title: '求职',
              icon: 'el-icon-s-operation',
              path: '',
              children: [
                {
                  name: 'DeliverResume',
                  title: '投递简历',
                  icon: 'el-icon-tickets',
                  path: '/student/deliverResume',
                  children: []
                }
              ]
            },
            {
              name: 'Setting',
              title: '设置',
              icon: 'el-icon-setting',
              path: '',
              children: [
                {
                  name: 'UserCenter_student',
                  title: '用户中心',
                  icon: 'el-icon-user-solid',
                  path: '/student/userCenter_student',
                  children: []
                },
                {
                  name: 'Other',
                  title: '其它设置',
                  icon: 'el-icon-picture',
                  path: '/sys/other',
                  children: []
                }
              ]
            }
          ]
        } else if (typeId === 1) {
          this.menuList = [
            {
              name: 'StudentManage',
              title: '学生管理',
              icon: 'el-icon-s-operation',
              path: '',
              children: [
                {
                  name: 'LookStudent',
                  title: '查看学生',
                  icon: 'el-icon-s-management',
                  path: '/teacher/lookStudent',
                  children: []
                },
                {
                  name: 'JobSituation',
                  title: '就业情况',
                  icon: 'el-icon-suitcase-1',
                  path: '/teacher/jobSituation',
                  children: []
                }
              ]
            },
            {
              name: 'Setting',
              title: '设置',
              icon: 'el-icon-setting',
              path: '',
              children: [
                {
                  name: 'UserCenter_teacher',
                  title: '用户中心',
                  icon: 'el-icon-user-solid',
                  path: '/teacher/userCenter_teacher',
                  children: []
                },
                {
                  name: 'Other',
                  title: '其它设置',
                  icon: 'el-icon-picture',
                  path: '/sys/other',
                  children: []
                }
              ]
            }
          ]
        } else if (typeId === 2) {
          this.menuList = [
            {
              name: 'RecruiterManage',
              title: '招聘管理',
              icon: 'el-icon-s-operation',
              path: '',
              children: [
                {
                  name: 'NewEmploy',
                  title: '发布招聘',
                  icon: 'el-icon-s-ticket',
                  path: '/recruiter/newEmploy',
                  children: []
                },
                {
                  name: 'LookResume',
                  title: '查看投递',
                  icon: 'el-icon-s-check',
                  path: '/recruiter/lookResume',
                  children: []
                }
              ]
            },
            {
              name: 'Setting',
              title: '设置',
              icon: 'el-icon-setting',
              path: '',
              children: [
                {
                  name: 'UserCenter_recruiter',
                  title: '用户中心',
                  icon: 'el-icon-user-solid',
                  path: '/recruiter/userCenter_recruiter',
                  children: []
                },
                {
                  name: 'Other',
                  title: '其它设置',
                  icon: 'el-icon-picture',
                  path: '/sys/other',
                  children: []
                }
              ]
            }
          ]
        } else if (typeId === 3) {
          this.menuList = [
            {
              name: 'AdminManage',
              title: '表格管理',
              icon: 'el-icon-s-operation',
              path: '',
              children: [
                {
                  name: 'AccountTable',
                  title: '账号表',
                  icon: 'el-icon-s-order',
                  path: '/admin/accountTable',
                  children: []
                },
                {
                  name: 'StudentTable',
                  title: '学生表',
                  icon: 'el-icon-s-order',
                  path: '/admin/studentTable',
                  children: []
                },
                {
                  name: 'TeacherTable',
                  title: '教师表',
                  icon: 'el-icon-s-order',
                  path: '/admin/teacherTable',
                  children: []
                },
                {
                  name: 'RecruiterTable',
                  title: '招聘者表',
                  icon: 'el-icon-s-order',
                  path: '/admin/recruiterTable',
                  children: []
                },
                {
                  name: 'AdministratorTable',
                  title: '管理员表',
                  icon: 'el-icon-s-order',
                  path: '/admin/administratorTable',
                  children: []
                },
                {
                  name: 'ClassTable',
                  title: '班级表',
                  icon: 'el-icon-s-order',
                  path: '/admin/classTable',
                  children: []
                },
                {
                  name: 'MajorTable',
                  title: '专业表',
                  icon: 'el-icon-s-order',
                  path: '/admin/majorTable',
                  children: []
                },
                {
                  name: 'SchoolTable',
                  title: '学校表',
                  icon: 'el-icon-s-order',
                  path: '/admin/schoolTable',
                  children: []
                },
                {
                  name: 'ProfessionTable',
                  title: '职业表',
                  icon: 'el-icon-s-order',
                  path: '/admin/professionTable',
                  children: []
                },
                {
                  name: 'CompanyTable',
                  title: '公司表',
                  icon: 'el-icon-s-order',
                  path: '/admin/companyTable',
                  children: []
                },
                {
                  name: 'EmployTable',
                  title: '招聘表',
                  icon: 'el-icon-s-order',
                  path: '/admin/employTable',
                  children: []
                },
                {
                  name: 'DeliverTable',
                  title: '投递表',
                  icon: 'el-icon-s-order',
                  path: '/admin/deliverTable',
                  children: []
                },
                {
                  name: 'FoundTable',
                  title: '开设表',
                  icon: 'el-icon-s-order',
                  path: '/admin/foundTable',
                  children: []
                },
                {
                  name: 'TypeTable',
                  title: '类型表',
                  icon: 'el-icon-s-order',
                  path: '/admin/typeTable',
                  children: []
                }
              ]
            },
            {
              name: 'Setting',
              title: '设置',
              icon: 'el-icon-setting',
              path: '',
              children: [
                {
                  name: 'UserCenter_admin',
                  title: '用户中心',
                  icon: 'el-icon-user-solid',
                  path: '/admin/userCenter_admin',
                  children: []
                },
                {
                  name: 'Other',
                  title: '其它设置',
                  icon: 'el-icon-picture',
                  path: '/sys/other',
                  children: []
                }
              ]
            }
          ]
        }
      })
    }
  },
  data() {
    return {
      menuList: []
    }
  },
  created() {
    this.modifyMenuList()
  }
}
</script>

<style scoped>
.el-menu {
  height: 100vh;
}

a {
  text-decoration: none;
}
a:link {
  font-size: 12px;
  color: #000000;
}
a:visited {
  font-size: 12px;
  color: #000000;
}
a:hover {
  font-size: 12px;
  color: #999999;
}

.router-link-active {
  text-decoration: none;
}
</style>