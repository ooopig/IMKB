<template>
  <div>
    id:{{this.$route.query.id}}
  </div>
</template>

<script>
export default {
name: "KbSpecific",
  data(){
  return{

  }
  },
  methods:{

  },
  mounted() {
    console.log(this.$route.query.id)
  },
  watch:{
    $route(val){
      console.log('specific',val)
    }
  }
}
</script>

<style scoped>

</style>
