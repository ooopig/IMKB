<template>
  <div>
    知识检索
    <p>{{this.$route.query.id}}</p>
  </div>
</template>

<script>
export default {
name: "KbSearch",
  data(){
    return{

    }
  },
  methods:{

  },
  mounted() {
    console.log(!this.$route.query.id)
  }
}
</script>

<style scoped>

</style>
