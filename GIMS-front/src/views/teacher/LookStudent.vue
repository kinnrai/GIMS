<template>
  <div>
    <el-form :inline="true">
      <el-form-item>
        <el-select v-model="currentClassId">
          <el-option :label="item.className" :key="index" :value="item.classId"  v-for="(item,index) in ownClass" @click.native="getStudentList"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-input v-model="searchForm.studentName" aria-placeholder="名称" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getStudentList">搜索</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="dialogVisible=true">新增</el-button>
      </el-form-item>
      <el-form-item>
        <el-popconfirm title="确认删除吗?" @confirm="handleDel(null)">
          <el-button type="danger" slot="reference" :disabled="delStatus">批量删除</el-button>
        </el-popconfirm>
      </el-form-item>
    </el-form>

    <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%" border stripe @selection-change="handleSelectionChange">
      <el-table-column type="selection"></el-table-column>
      <el-table-column prop="studentId" label="学生id"></el-table-column>
      <el-table-column prop="studentName" label="学生名"></el-table-column>
      <el-table-column prop="sex" label="性别">
        <template v-slot="scope">
          <el-tag size="small" v-if="scope.row.sex===0" type="success">男</el-tag>
          <el-tag size="small" v-else-if="scope.row.sex===1" type="success">女</el-tag>
          <el-tag size="small" v-else type="danger">未知</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="birthday" label="生日"></el-table-column>
      <el-table-column prop="credit" label="学分"></el-table-column>
      <el-table-column prop="resume" label="简历"></el-table-column>
      <el-table-column prop="icon" label="操作">
        <template v-slot="scope">
          <el-button type="text" @click="handleEdit(scope.row.studentId)">修改</el-button>
          <el-divider direction="vertical"></el-divider>
          <template>
            <el-popconfirm title="确认删除吗?" @confirm="handleDel(scope.row.studentId)">
              <el-button type="text" slot="reference">删除</el-button>
            </el-popconfirm>
          </template>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        layout="total, sizes, prev, pager, next, jumper"
        :current-page="currentPage"
        :page-sizes="[5, 10, 20, 50]"
        :page-size="pageSize"
        :total="totalPage">
    </el-pagination>

    <el-dialog title="提示" :visible.sync="dialogVisible" width="600px" :before-close="handleClose">
      <el-form :model="editForm" :rules="rules" ref="editForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="学生名" prop="studentName" label-width="100px">
          <el-input v-model="editForm.studentName" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="sex" label-width="100px">
          <el-radio-group v-model="editForm.sex">
            <el-radio :label="0">男</el-radio>
            <el-radio :label="1">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="生日">
          <el-date-picker type="date" placeholder="选择日期" v-model="editForm.birthday" style="width: 100%;"></el-date-picker>
        </el-form-item>
        <el-form-item label="班级" prop="classId" label-width="100px">
          <el-select v-model="editForm.classId" placeholder="请选择班级" @click.native="getAllClass">
            <el-option :label="classs.className" :key="index" :value="classs.classId" v-for="(classs,index) in allClass"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="学分" prop="credit" label-width="100px">
          <template>
            <el-input-number v-model="editForm.credit" :precision="2" :step="0.1" :min="0" :max="5"></el-input-number>
          </template>
        </el-form-item>
        <el-form-item label="简历" prop="resume" label-width="100px">
          <el-input type="textarea" v-model="editForm.resume"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('editForm')">立即创建</el-button>
          <el-button @click="resetForm('editForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

  </div>
</template>

<script>
export default {
  name: "LookStudent",
  data() {
    return {
      searchForm: {
        studentName: ''
      },
      delStatus: true,
      multipleSelection: [],
      tableData: [],
      currentPage: 1,
      pageSize: 10,
      totalPage: 1,
      dialogVisible: false,
      editForm: {
        studentId: undefined,
        studentName: '',
        sex: undefined,
        birthday: undefined,
        classId: undefined,
        credit: undefined,
        resume: '',
        accountId: undefined
      },
      rules: {
        studentName: [
          {required: true, message: '请输入学生名', trigger: 'blur'}
        ],
        classId: [
          {required: true, message: '请选择班级', trigger: 'change'}
        ]
      },
      allClass: [],
      accountNotUse: [],
      accountId: undefined,
      ownClass: [],
      currentClassId: undefined
    }
  },
  created() {
    this.getAccountId()
  },
  methods: {
    toggleSelection(rows) {
      if (rows) {
        rows.forEach(row => {
          this.$refs.multipleTable.toggleRowSelection(row);
        });
      } else {
        this.$refs.multipleTable.clearSelection();
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
      this.delStatus = (val.length === 0);
    },
    handleEdit(id) {
      this.$axios.get('/student/info/' + id)
          .then(res => {
            this.editForm = res.data.data
            this.dialogVisible = true
          })
    },
    handleDel(id) {
      const ids = []
      if (id !== null) {
        ids.push(id)
      } else {
        this.multipleSelection.forEach(row => {
          ids.push(row.studentId)
        })
      }
      this.$axios.post('/student/delete', ids)
          .then(res => {
            this.$message({
              showClose: true,
              message: '删除成功',
              type: 'success',
              onClose: () => {
                this.getStudentList()
              }
            })
          })
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.getStudentList()
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.getStudentList()
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            this.editForm = {};
            done();
          })
          .catch(_ => {
          });
    },
    getStudentList() {
      this.$axios.get('/student/list', {
        params: {
          studentName: this.searchForm.studentName,
          classId: this.currentClassId,
          currentPage: this.currentPage,
          pageSize: this.pageSize
        }
      }).then(res => {
        this.tableData = res.data.data.records
        this.currentPage = res.data.data.current
        this.pageSize = res.data.data.size
        this.totalPage = res.data.data.total
      })
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post('/student/' + (this.editForm.studentId ? 'update' : 'add'), this.editForm)
              .then(res => {
                this.$message({
                  showClose: true,
                  message: '操作成功',
                  type: 'success',
                  onClose: () => {
                    this.editForm.studentName = ''
                    this.getStudentList()
                  }
                });
                this.editForm.accountId = undefined
                this.dialogVisible = false
              })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    getAllClass() {
      this.$axios.get('/class/getAllClass')
          .then(res => {
            this.allClass = res.data.data
          })
    },
    getAccountId() {
      this.$axios.get("/sys/userInfo").then(res => {
        this.accountId = res.data.data.accountId
        this.getOwnClass()
      })
    },
    getOwnClass() {
      this.$axios.post('/class/ownClass', this.accountId).then(res => {
        this.ownClass = res.data.data
        this.currentClassId = this.ownClass[0].classId
        this.getStudentList()
      })
    }
  }
}
</script>

<style>
.el-pagination {
  float: right;
  margin-top: 20px;
}
</style>