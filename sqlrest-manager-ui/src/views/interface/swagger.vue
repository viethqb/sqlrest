<template>
  <div style="height:auto;">
    <iframe id="iframe"
            v-loading="loading"
            :src="url"
            width="100%"
            @load="resizeIframe"
            style="min-height:300px; width:100%;border:0;"
            frameborder="0"></iframe>
  </div>
</template>
 
<script>

export default {
  name: 'SwaggerUI',
  data () {
    return {
      loading: false,
      url: ''
    }
  },
  methods: {
    resizeIframe: function (event) {
      // Set iframe height to its content height
      var ifm = document.getElementById("iframe");
      ifm.height = document.documentElement.clientHeight;
      ifm.width = document.documentElement.clientWidth;

      this.$http({
        method: "GET",
        url: "/sqlrest/manager/api/v1/node/gateway"
      }).then(
        res => {
          if (0 === res.data.code) {
            if (res.data.data && typeof res.data.data === 'string') {
              this.url = res.data.data + '/apidoc/index.html';
              console.log(this.url)
              // Remove loading effect after loading completes
              this.loading = false;
            }
          } else {
            if (res.data.message) {
              alert("Failed to load data: " + res.data.message);
            }
          }
        }
      );
    }
  },
  mounted () {
    this.loading = true;
  },
}
</script>
 
<style>
/* Custom styles can be added here */
</style>