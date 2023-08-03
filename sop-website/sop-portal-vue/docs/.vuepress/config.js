module.exports = {
    locales: {
        // 键名是该语言所属的子路径
        // 作为特例，默认语言可以使用 '/' 作为其路径。
        '/': {
            lang: 'zh-CN', // 将会被设置为 <html> 的 lang 属性
            title: 'XX开放平台',
            description: 'XX开放平台'
        },
        // '/zh/': {
        //     lang: 'zh-CN',
        //     title: 'VuePress',
        //     description: 'Vue 驱动的静态网站生成器'
        // }
    },
    // 主题配置
    themeConfig: {
        logo: '/assets/images/logo.png',
        search: false,
        searchMaxSuggestions: 10,
        // 导航按钮配置
        nav: [
            { text: '登录', link: 'http://localhost:8083/index.html#/login' },
            { text: '免费注册', link: 'http://localhost:8083/index.html#/isvReg' },
        ],
    },
    // 派生主题，https://www.vuepress.cn/theme/inheritance.html#%E6%A6%82%E5%BF%B5
    extend: '@vuepress/theme-default',
    // 插件
    plugins: ['@vuepress/back-to-top']
}
