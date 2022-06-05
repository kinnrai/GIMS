import Vue from 'vue'
import VueRouter from 'vue-router'

import Home from "@/views/Home";
import Login from "@/views/Login";
import Index from "@/views/Index";
import UserSecurity from "@/views/sys/UserSecurity";
import AccountTable from "@/views/admin/AccountTable";
import TypeTable from "@/views/admin/TypeTable";
import SchoolTable from "@/views/admin/SchoolTable";
import MajorTable from "@/views/admin/MajorTable";
import CompanyTable from "@/views/admin/CompanyTable";
import ProfessionTable from "@/views/admin/ProfessionTable";
import AdministratorTable from "@/views/admin/AdministratorTable";
import TeacherTable from "@/views/admin/TeacherTable";
import RecruiterTable from "@/views/admin/RecruiterTable";
import ClassTable from "@/views/admin/ClassTable";
import EmployTable from "@/views/admin/EmployTable";
import FoundTable from "@/views/admin/FoundTable";
import StudentTable from "@/views/admin/StudentTable";
import NewEmploy from "@/views/recruiter/NewEmploy";
import UserCenter_recruiter from "@/views/recruiter/UserCenter_recruiter";
import Other from "@/views/sys/Other";
import UserCenter_admin from "@/views/admin/UserCenter_admin";
import DeliverResume from "@/views/student/DeliverResume";
import UserCenter_student from "@/views/student/UserCenter_student";
import LookStudent from "@/views/teacher/LookStudent";
import UserCenter_teacher from "@/views/teacher/UserCenter_teacher";
import DeliverTable from "@/views/admin/DeliverTable";
import LookResume from "@/views/recruiter/LookResume";
import JobSituation from "@/views/teacher/JobSituation";


Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'Home',
        component: Home,
        children: [
            {
                path: '/index',
                name: 'Index',
                component: Index,
                meta: {
                    title: '首页'
                }
            },
            {
                path: '/sys/userSecurity',
                name: 'UserSecurity',
                component: UserSecurity,
                meta: {
                    title: '账号安全'
                }
            },
            {
                path: '/sys/other',
                name: 'Other',
                component: Other,
                meta: {
                    title: '上传头像'
                }
            },
            {
                path: '/admin/accountTable',
                name: 'AccountTable',
                component: AccountTable,
                meta: {
                    title: '账号表'
                }
            },
            {
                path: '/admin/typeTable',
                name: 'TypeTable',
                component: TypeTable,
                meta: {
                    title: '类型表'
                }
            },
            {
                path: '/admin/schoolTable',
                name: 'SchoolTable',
                component: SchoolTable,
                meta: {
                    title: '学校表'
                }
            },
            {
                path: '/admin/majorTable',
                name: 'MajorTable',
                component: MajorTable,
                meta: {
                    title: '专业表'
                }
            },
            {
                path: '/admin/companyTable',
                name: 'CompanyTable',
                component: CompanyTable,
                meta: {
                    title: '公司表'
                }
            },
            {
                path: '/admin/professionTable',
                name: 'ProfessionTable',
                component: ProfessionTable,
                meta: {
                    title: '职业表'
                }
            },
            {
                path: '/admin/administratorTable',
                name: 'AdministratorTable',
                component: AdministratorTable,
                meta: {
                    title: '管理员表'
                }
            },
            {
                path: '/admin/teacherTable',
                name: 'TeacherTable',
                component: TeacherTable,
                meta: {
                    title: '教师表'
                }
            },
            {
                path: '/admin/recruiterTable',
                name: 'RecruiterTable',
                component: RecruiterTable,
                meta: {
                    title: '招聘者表'
                }
            },
            {
                path: '/admin/classTable',
                name: 'ClassTable',
                component: ClassTable,
                meta: {
                    title: '班级表'
                }
            },
            {
                path: '/admin/employTable',
                name: 'EmployTable',
                component: EmployTable,
                meta: {
                    title: '招聘表'
                }
            },
            {
                path: '/admin/foundTable',
                name: 'FoundTable',
                component: FoundTable,
                meta: {
                    title: '开设表'
                }
            },
            {
                path: '/admin/studentTable',
                name: 'StudentTable',
                component: StudentTable,
                meta: {
                    title: '学生表'
                }
            },
            {
                path: '/admin/deliverTable',
                name: 'DeliverTable',
                component: DeliverTable,
                meta: {
                    title: '投递表'
                }
            },
            {
                path: '/recruiter/newEmploy',
                name: 'NewEmploy',
                component: NewEmploy,
                meta: {
                    title: '发布招聘'
                }
            },
            {
                path: '/recruiter/lookResume',
                name: 'LookResume',
                component: LookResume,
                meta: {
                    title: '投递简历'
                }
            },
            {
                path: '/recruiter/userCenter_recruiter',
                name: 'UserCenter_recruiter',
                component: UserCenter_recruiter,
                meta: {
                    title: '用户中心'
                }
            },
            {
                path: '/admin/userCenter_admin',
                name: 'UserCenter_admin',
                component: UserCenter_admin,
                meta: {
                    title: '用户中心'
                }
            },
            {
                path: '/student/deliverResume',
                name: 'DeliverResume',
                component: DeliverResume,
                meta: {
                    title: '投递简历'
                }
            },
            {
                path: '/student/userCenter_student',
                name: 'UserCenter_student',
                component: UserCenter_student,
                meta: {
                    title: '用户中心'
                }
            },
            {
                path: '/teacher/lookStudent',
                name: 'LookStudent',
                component: LookStudent,
                meta: {
                    title: '查看学生'
                }
            },
            {
                path: '/teacher/jobSituation',
                name: 'JobSituation',
                component: JobSituation,
                meta: {
                    title: '就业情况'
                }
            },
            {
                path: '/teacher/userCenter_teacher',
                name: 'UserCenter_teacher',
                component: UserCenter_teacher,
                meta: {
                    title: '用户中心'
                }
            }
        ]
    },
    {
        path: '/login',
        name: 'Login',
        component: Login,
        meta: {
            title: '登录'
        }
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

router.beforeEach((to, from, next) => {

    //修改标题
    if (to.meta.title) {
        document.title = to.meta.title
    }

    //销毁token后返回登录页
    let token = localStorage.getItem("token")
    if (!token && to.path !== '/login') {
        next({path: '/login'})
    }

    next()
})

export default router
