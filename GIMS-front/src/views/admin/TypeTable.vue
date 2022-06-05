<template>
  <div>
    <el-form :inline="true">
      <el-form-item>
        <el-input v-model="searchForm.typeName" aria-placeholder="名称" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getTypeList">搜索</el-button>
      </el-form-item>
    </el-form>

    <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%" border stripe @selection-change="handleSelectionChange">
      <el-table-column type="selection"></el-table-column>
      <el-table-column prop="typeId" label="类型id"></el-table-column>
      <el-table-column prop="typeName" label="类型名"></el-table-column>
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

  </div>
</template>

<script>
export default {
  name: "TypeTable",
  data() {
    return {
      searchForm: {
        typeName: ''
      },
      delStatus: true,
      multipleSelection: [],
      tableData: [],
      currentPage: 1,
      pageSize: 10,
      totalPage: 1,
      dialogVisible: false,
    }
  },
  created() {
    this.getTypeList()
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
    handleSizeChange(val) {
      this.pageSize = val
      this.getTypeList()
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.getTypeList()
    },
    getTypeList() {
      this.$axios.get('/type/list', {
        params: {
          typeName: this.searchForm.typeName,
          currentPage: this.currentPage,
          pageSize: this.pageSize
        }
      }).then(res => {
        this.tableData = res.data.data.records
        this.currentPage = res.data.data.current
        this.pageSize = res.data.data.size
        this.totalPage = res.data.data.total
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