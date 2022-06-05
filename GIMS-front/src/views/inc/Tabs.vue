<template>
  <div>
    <el-tabs v-model="editableTabsValue" type="card" closable @tab-remove="removeTab" @tab-click="clickTab">
      <el-tab-pane v-for="(item, index) in editableTabs" :key="item.name" :label="item.title" :name="item.name">

      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
export default {
  name: "Tabs",
  computed: {
    editableTabsValue: {
      get() {
        return this.$store.state.menus.editableTabsValue
      },
      set(value) {
        this.$store.state.menus.editableTabsValue = value
      }
    },
    editableTabs: {
      get() {
        return this.$store.state.menus.editableTabs
      },
      set(value) {
        this.$store.state.menus.editableTabs = value
      }
    }
  },
  methods: {
    removeTab(targetName) {
      let tabs = this.editableTabs;
      let activeName = this.editableTabsValue;
      if (targetName === 'Index') {
        return;
      }
      if (activeName === targetName) {
        tabs.forEach((tab, index) => {
          if (tab.name === targetName) {
            let nextTab = tabs[index + 1] || tabs[index - 1];
            if (nextTab) {
              activeName = nextTab.name;
            }
          }
          this.$router.push({name: activeName})
              .catch(err => {
              })
        });
      }

      this.editableTabsValue = activeName;
      this.editableTabs = tabs.filter(tab => tab.name !== targetName);
    },
    clickTab(target) {
      this.$router.push({name: target.name})
          .catch(err => {
          })
    }
  }
}
</script>

<style scoped>

</style>