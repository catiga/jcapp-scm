<template>
 <span :endTime="endTime" :callback="callback" :endText="endText">
  <slot>
   {{content}}
  </slot>
 </span>
</template>
<script>
	import api from '@/config/api';
	
    export default {
        data() {
            return {
                content: '',
                endTime:'',
            }
        },
        props: {
            // endTime: {
            //     type: String,
            //     default: ''
            // },
            endText: {
                type: String,
                default: '已结束'
            },
            callback: {
                type: Function,
                default: ''
            }
        },
        mounted() {
        	this.root = api.rootUrl;
            this.getInfo();
        },
        methods: {
            getInfo() {
                this.axios.get(this.root + 'index/total',).then((response) => {
                    console.log(response);
                    this.infoData = response.data.data;
                    let time = response.data.data.timestamp;
                    this.endTime = time.toString();
                    this.countdowm(this.endTime)
                })
            },
            countdowm(timestamp) {
                let self = this;
                let timer = setInterval(function () {
                    let nowTime = new Date();
                    let endTime = new Date(timestamp * 1000);
                    let t = endTime.getTime() - nowTime.getTime() - 10;
                    if (t > 0) {
                        let day = Math.floor(t / 86400000);
                        let hour = Math.floor((t / 3600000) % 24);
                        let min = Math.floor((t / 60000) % 60);
                        let sec = Math.floor((t / 1000) % 60);
                        hour = hour < 10 ? "0" + hour : hour;
                        min = min < 10 ? "0" + min : min;
                        sec = sec < 10 ? "0" + sec : sec;
                        let format = '';
                        if (day > 0) {
                            format = `${day}天${hour}小时${min}分${sec}秒`;
                        }
                        if (day <= 0 && hour > 0) {
                            format = `${hour}小时${min}分${sec}秒`;
                        }
                        if (day <= 0 && hour <= 0) {
                            format = `${min}分${sec}秒`;
                        }
                        self.content = format;
                    } else {
                        clearInterval(timer);
                        self.content = self.endText;
                        self._callback();
                    }
                }, 1000);
            },
            _callback() {
                if (this.callback && this.callback instanceof Function) {
                    this.callback();
                }
            }
        }
    }
</script>