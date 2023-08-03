**比翼鸿信6.9.3版本**

**一、新功能:**

```tex
1.流程编排
     扩展人工任务配置栏的多实例配置项，增加多实例策略选项，包括一票通过、票数通过、比例通过策略，并增加对应的票数和比例配置
      
2.低代码
    1）新建页面时支持按照页面配置项选项进行页面初始化。
    2）主表页面时绑定数据表时支持主表和子表多选。
    3）生成页面的列表项支持点击直接修改编辑。
    
```

**二、功能增强与优化:**

```tex
1.<流程编排>修复子流程中嵌套的人工任务，无法在规则配置处读任务取并配置；
2.<流程编排>修复流程运行时规则配置处人员配置指定自定义脚本无法动态加载处理人的问题；
3.完成mybatis plus 的框架适配，解决mybatis plus集成过程中的依赖问题；
4.<代码生成模块>丰富框架内置模板类型：新增mybatis plus内置代码生成模板，支持单表、树表、主子表基础表单列表页面前后端代码的快速生成；
5.<代码生成模块>修复树表模板生成树页面渲染过慢问题；
6.<用户登录>增加账号错误次数检查前端开关，修复接口持续调用错误问题；
7.<字典管理>修复树状字典渲染过慢问题；
8.<日志管理>修复多租户下操作日志总记录数显示问题；
9.集成规则引擎liteflow，修复liteflow集成中的问题。
```


**比翼鸿信6.9.2版本**

**一、新功能:**

```tex
1、低代码编程
    对数据模型创建、页面设计做了优化，使数据模型创建更加多样性，页面列表设计做了修改，引入了列表设计器，实现列表设计的可视化操作、引入了导入导出功能，
    并且按钮权限、按钮显隐做成了可配置，可适配更多的业务场景。
    1）增加数据模型的创建方式，可通过已有的数据表及视图创建数据模型，通过新增表的方式创建数据模型，对数据表的表名、主键、表字段、主子表外键做了重复性校验。
    2）列表设计增加了列表设计器，可通过列表设计器对列表进行设计。
    3）增加了导入、导出功能，可通过按钮配置导入、导出事件实现列表导入导出。
    4）增加了按钮的显、隐就接口权限控制，通过角色绑定相应页面按钮实现页面按钮显隐、及按钮绑定接口权限的控制。
    
2、流程编排服务
    1）新增流程节点多实例策略配置，提供一票否决，全票通过两种策略；      
    2）改造表单设计，新增路由表单，支持现有页面或自定义页面接入工作流；     
    3）新增表单字段显示控制功能，对已绑定表单中字段进行显示权限的控制，选择对哪些字段能够进行展示。

```

**二、功能增强与优化:**

```tex
1.<流程编排>移动数据表管理菜单至流程编排菜单下面，优化数据表选择滚动条及建表默认字段
2.<流程编排>支持快建流程的同时，恢复原有创建流程按钮
3.<流程编排>优化规则配置操作界面，减少弹窗
4.新增默认的全局线程池配置Bean，修复流程编排单独引入报错问题
5.框架后端功能模块拆分，新增core、oauth、componentcall、util、captcha通用模块
6.框架前端代码优化，将页面接口分模块进行拆分与整理，统一页面接口管理
7.基于前端接口统一管理，对代码生成前端模板进行改造，统一化前端代码风格。
8.解决部门过多情况下，部门树加载过慢问题
9.引入自定义主键生成策略类BaseAutoIdProcess,可以通过继承的方式，自定义setFieldValue方法，从而实现主键自定义生成规则。如新增UUIDAutoIdProcess策略，通过添加字段注解适配老版本varchar字符型主键
10.解决登录日志登录失败，日志记录成功的异常问题
11.代码生成模板整改适配P3C规范（抽象类）
12.修复树状字典字典项删除问题，增加子节点存在时父节点不允许删除校验
13.统一页面风格，解决个人中心页面按钮风格问题
14.前端页面优化，修复列表缩放表格变形问题
15.修复组件日志ES上传失败的问题
16.修复wangedit富文本编辑器表格布局与展示问题及文件上传问题，支持minio服务器配置
17.验证码功能扩展，支持通过配置来灵活控制验证码的显示及隐藏状态
```

**比翼鸿信6.9.1版本**

**一、新功能:**

```tex
1.系统参数配置
	系统参数管理模块主要用于前端代码中参数配置项的配置管理。可以将原来配置文件中的配置项提取出来，交由系统参数配置来进行维护，提供了前端配置操作的API接口，将原本直接使用配置文件的参数，改为通过查询系统参数表查询获取，使得前端的配置操作更加灵活。
2.敏感词管理 
	在实际开发系统中，往往面向的是大群体用户，用户来源和数据往往是无法预知的，从而用户的输入也是无法控制的，当用户在无意或者有意的情况下输入的内容包含了一些敏感词，将会对信息发布者和平台提供者带来不可预知的严重影响。所有急需一个功能模块，能够实现敏感词管理、敏感词检测及敏感词自动处理的能力。
3.数据库文档管理
	数据文档是基于 Screw (opens new window)工具，生成数据库表结构的文档。支持页面在线预览，也支持数据表结构的导出html、word、markDown格式文件 。
4.岗位管理
	提供了岗位管理的新增、修改、查询、删除的功能，并且在用户管理可以查看和编辑岗位信息。
5.错误码管理 
	系统所有错误码的管理，可在线修改错误提示，无需重启服务。支持手动、自动两种方式添加错误码。
6.流程编排 
	该功能模块是工作流程开发的一整套解决方案，该模块集成了表单设计器、模型设计器、流程驱动引擎的三项主要功能；
	用户可以通过表单设计器设计业务表单、模型设计器设计业务流程模型，以及核心的流程驱动引擎驱动流程的运转，以实现工作流业务的低代码开发。 
    （1）新增引导式步骤条，简化新建流程方式 ，
    （2）将表单绑定、流程设计与列表数据字段相结合，简化操作。
    （3）分配规则操作权限新增指定下一节点处理人以及终止功能 。
    （4）分配规则规则类型新增部门、以及自定义脚本，并提供流程发起人的自定义脚本。支持扩展自定义脚本 ，
    （5）新增流程统计，对系统中的流程进行统计，包含：流程模型数量、流程实例数、流程完成率、任务完成率、任务趋势、以及最新的流程实例信息。 
    （6）新增热门流程，根据流程实例数进行统计，统计维度分为：日报、周报、季报、年报，以及各阶段前几名等。
7.低代码编程
	设计流程做了修改，使业务构建过程更加清晰，更加有层次感，并且在构建过程中实现了模块、数据表、页面之间的绑定，使用户操作更加容易，具体构建顺序为：新建模块、创建数据模型并绑定模块、创建页面并绑定模块，并选择模块下的数据模型 。
    1）增加模块管理，在单个模块下可以构建多个数据模型、多个页面，并且通过模型管理可以实现数据模型管理，页面管理页面的跳转。 
    2）增加数据模型管理，可以通过新建数据模型实现数据源的绑定、模块的绑定，业务表的创建，目前支持单表、主子表， 创建主子表时可以配置关联字段。
    3）增加页面管理，可以通过新建页面实现模块的绑定，根据绑定的模块获取对应的数据模型，支持单表页面、主表页面、子表页面，在创建子表页面时可以通过绑定的模块选择对应的主表页面，实现主子表页面的绑定。
```

**二、功能增强与优化:**

```tex
1.在非租户户模式下，支持cas统一认证 
2.页面布局进行了优化，导航模式支持侧向导航栏和横向导航栏 
3.后台多租户配置改为ctsi:sassModel进行配置，saas模式：true,非saas模式：false,默认：false
4.低代码列表配置，生成列表支持
```

**比翼鸿信6.9版本**

**一、新功能:**

```tex
新功能
1.数据表管理
低代码新增在线数据表管理模块，通过该模块可以实现业务表的在线创建与维护能力。
1）提供数据表的新建、模糊检索及删除操作（仅支持空表删除）。此外还可以通过右键单击数据表展示操作区域，调出数据表新建和数据表复制功能。
2）提供数据库表字段的新增、更新及删除操作。新表创建默认添加内置字段（主键id、租户id、创建人、创建时间、更新人、更新时间），支持内置字段的编辑及删除。
2.低代码编程（多表配置可运行）
低代码编程模块，新增多表配置，基于该模块，开发人员无需掌握编程技能也可以快速构建单表基础应用，即零代码开发。采用引导式编程方式，优化低代码编程操作步骤，只需按照规范的引导步骤即可完成多表的主表/子表的表单列表页面的渲染，从而实现多表的基础功能操作。
1）新建模块增加表单类型字段，支持单表、多表、流程表多种类型模块的创建
2）优化单表和多表的主流程，调整列表配置与表单配置的顺序，列表配置增加表单相关配置字段，实现表单字段组件的表单同步展示，支持表单的再设计。
3）表单设计阶段支持容器化重新布局设计、表单字段的重新绑定及选择类组件（单选、多选、下拉）接入基础字典。
4）表单设计器新增人员选择和部门选择两个高级组件，使得表单的设计更加灵活，人员部门的选择更加方便快捷。
5）支提供多子表模块配置及子表表单列表配置，实现子表外键与主表主键关联数据绑定与展示，并且可以通过排序字段设置子表的展示顺序。通过主表”查看更多”可以对子表信息进行维护。
3.流程编排
该功能模块是工作流程开发的一整套解决方案，该模块集成了表单设计器、模型设计器、流程驱动引擎的三项主要功能；用户可以通过表单设计器设计业务表单、模型设计器设计业务流程模型，以及核心的流程驱动引擎驱动流程的运转，以实现工作流业务的低代码开发。
4.实时日志
根据页面操作同步查看相关的数据库表操作记录和执行的接口信息，包括操作时间，操作字段，操作表，参数值，访问类及访问接口等
```
**二、功能增强与优化:**

```tex       
1.调整并优化图标选择样式，使得图标的选择更加方便快捷。
2.调整操作日志逻辑，增加put和post类型参数的采集与呈现
3.修复swagger模拟登录接口登录报错问题
4.调整操作日志处理逻辑，调整参数和错误信息保存最长的长度，参数设置为3000，错误信息设置为1000
5.调整组件调用统计ES配置信息，更新ES集群地址为es-udp.chandao27-udp6hx-dev-env.es.devops.com:80
6.增加菜单拖拽管理页面。支持拖拽式调整菜单
7.修复低代码模块多租户适配问题
8.修复低代码模块单表在线开发单表批量删除问题
9.去除老版本工作流模块
10.增强表单设计器功能，扩展人员选择和部门选择高级组件
11.增强菜单管理模块，增加角色菜单互选的功能
```



**比翼鸿信6.8.3版本**

**一、新功能:**
```tex
1、新增表单设计器功能
集成页面表单设计器，提供多种容器组件及字段组件可供选择，通过拖拉拽方式结合组件设计及表单设计，
完成表单页面的布局及页面设计工作，支持页面预览及前端页面代码生成。轻松完成前端表单页面的整合与集成。
2、低代码编程（单表配置可运行）
新增低代码编程模块，基于该模块，开发人员无需掌握编程技能也可以快速构建单表基础应用，即零代码开发。
采用引导式编程方式，优化低代码编程操作步骤，只需通过：
配置数据表（绑定数据库已有的业务表）--表单设计（业务表表单设计）--列表配置（业务表列表设计）--菜单配置 
四个步骤即可完成单表的表单列表页面的渲染和增删改查基础功能使用。
3、可视化BI报表
集成ureport2通用报表设计器，提供多种数据源接入及数据集创建，通过数据源、数据集的管理，结合报表设计器、
图表设计器、自定义内置函数、报表属性设置，轻松完成基础报表或交叉表的设计及创建，支持报表的在线预览、参数过滤以及套件打印操作。
4、系统监控功能
新增系统监控模块，提供了系统基础监控能力，包括CPU、内存、磁盘、JVM以及服务器的基本信息。
支持监控数据的时间区间查询，便于问题的发现与排查。
5、解析模板（Excel）
新增excel导入功能，并对之前版本excel导出功能进行了一定优化，通过注解的方式，对导入导出功能进行了统一的封装，
简化了代码操作步骤。只需在实体字段通过@Excel注解结合注解属性type及celltype，即可完成导入/导出字段的绑定和字段属性的设置。
6、多数据源配置
新增多数据配置，通过配置文件结合切面注解的方式，实现多数据源的动态切换，支持数据源的无限添加，使用时通过注解指定需要操作的数据源即可。
集成高性能数据库连接池druid，支持多数据源页面监控。
7、研发社区
为了提供用户更好的框架支持，开发部署研发社区项目，内容建设包含：总体介绍、技术架构、版本更新、快速入门、环境部署、功能模块、
功能扩展、低代码、工作流、常见问题、编码规范、安全检测（待完善）和知识沙龙（待完善）等。
项目地址：http://udp6hxwiki.devops.com/
```
**二、功能增强与优化:**

```tex
1、优化通知公告模块的富文本编辑器，使用wangeditor5替换原始的UEditor编辑器，风格更加整洁，
功能更加强大，稳定性和扩展性在一定程度上都进行了增强。
2、修复代码生成模块表单编辑时控制台报错问题。
3、修复代码生成主子表代码生成下载子表代码无法下载问题。
4、框架后端代码按照模块进行闭包处理，对外以jar包方式提供，便于按需引入及版本升级适配。
5、登录操作日志添加租户信息，租户数据隔离。
6、模块添加操作日志注解。
7、修复数据字典项编辑后主键未清空。
8、列表批量删除，点击重置后，删除信息未清空。
9、定时任务添加调度日志
```

**比翼鸿信6.8.2版本**

**一、新功能:**

```tex
	1、扩展代码生成模块功能，新增自定义代码模板管理功能
		支持自定义代码模板的上传。
		提供自定义代码模板的页面维护。
		提供代码生成自定义代码模板的选择，在表单管理配置中可以根据需要选择自定义模板生成对应的前后端代码。
	2、多租户开关
		新增多租户开关配置，可以根据业务需要选择多租户版本还是非租户版本，默认为非租户版本
	3、扩展系统登录方式，接入第三方登录
		支持天翼账号登录，包括天翼扫码、天翼手机号邮箱登录
		支持企业微信扫码登录
	4.扩展验证码类型，接入行为验证码
		提供普通字符验证码验证方式
		提供滑动拼图验证码方式
		提供文字点选验证码验证方式
		支持验证码切换配置
```

**二、功能增强与优化:**

```tex
	1、修复了主子表生成代码主题切换的问题；
	2、优化了菜单管理模块，修复了菜单编辑自动折叠问题；
	3、修复菜单管理，排序默认字段校验问题；
	4、修复部门管理，部门过多时列表树无法全部展示问题；
	5、优化部门管理，部门列表倒序排列
	6、更新组件统计模块ES配置信息
	7、系统默认配置为非租户版本
```



**比翼鸿信6.8.1版本**

**一、新功能:**

```tex
1、扩展代码生成模块功能，支持树表和主子表
    代码生成新增树表、主子表支持，可以通过代码生成模块生成树表和主子表的增删改查基础接口及简单的前端表单和列表界面。
    ①提供树表代码生成及其相关配置。
    ②提供主子表代码生成及其相关配置。
    ③提供树表/主子表代码生成页面预览，可以通过预览功能实现生成代码的实时预览。
    ④新增树表代码生产相关前后端模板。
    ⑤新增主子表代码生成相关前后端模板。
2、新增组件调用统计功能
    新增比翼框架组件调用能力统计功能，为效能平台提供框架组件使用情况数据指标的接入与统计。
    现阶段组件划分为：
        ①权限认证组件:hx-admin；
        ②工作流组件:hx-activiti；
        ③代码生成组件:hx-generator；
        ④数据字典组件:hx-dictionary；
        ⑤消息队列管理组件:hx-message-queue；
    配置定时任务定期上传组件调用数据至ElasticSearch
    结合ElasticSearch综合数据分析各组件调用情况
    分析维度
    ①日度调用统计：指定范围内分析每日组件调用情况（调用总计次数，各组件调用次数），若未指定时间范围，默认查询当月每日的数据；
    ②月度调用统计:查询当年每月的组件调用情况（调用总计次数，各组件调用次数）；
    ③年度调用统计:以年为粒度查询所有的组件调用情况（调用总计次数，各组件调用次数）；
```

**二、功能增强与优化:**

```tex
①修复了单表生成代码主键插入报错问题；
②修复了单表代码生成创建人空问题；
③修复代码生成后端字段校验出错问题及编辑校验闪现问题；
④调整表单管理列表页展示，新增表单倒序排列；
⑤修复代码生成代码获取字典项，默认最多显示20条；
⑥修复代码生成字段备注默认值；
⑦增加菜单目录排序功能；
⑧增加代码生成-表单管理页面的字段校验及错误提示；
⑨新增页面组件复选框，支持复选框字典绑定与查询；
⑩新增生成代码列表字段列宽度配置功能；
⑪新增生成代码表单字段前端的校验功能。
```

**比翼鸿信6.8版本**

**一、新功能:**

```tex
    1、新增字典管理模块：
        统一采用字符类型字典编码进行字典存储，增强了数据字典的可读性和可维护性。
        丰富了数据字典类型，提供树状字典和基础字典可供用户进行选择。
        对于基础字典，提供了多种创建方式与接入方式（页面手动维护，自定义字典和SQL字典）。
    2、新增代码生成模块：
        提供单表表结构多种方式维护（页面构建和数据库导入）。
        提供表字段前端代码表单列表属性页面配置，包括表单列表是否显示、组件类型、组件大小，查询类型、数据字典绑定以及字段通用校验。
        提供了表结构的页面化维护与数据库同步功能。
        提供生成代码的页面预览与下载功能，下载支持zip包页面下载和自定义本地路径下载。
```

**二、功能增强与优化:**

```tex
    1、移除了上一版本的数据字典模块。
    2、移除了上一版本的自定义表单模块。
    3、修复了工作流模块租户流程信息分离问题。
    4、修复了消息队列管理table伸缩问题。
    5、统一了消息队列管理后端代码目录结构问题。
    6、修复用户管理角色编辑报错问题。
    7、修复日志管理ip地址错误获取问题。
    8、将任务列表模块中数据字典接口迁移到字典管理对应接口。
```



**比翼鸿信6.7版本**

**一、新功能:**

```tex
1.Saaas化改造
	支持应用Saas化，增加租户概念，对租户间数据(用户数据，角色数据，菜单数据，部门数据)进行隔离。
	支持系统租户管理员和普通租户两种系统角色。不同角色提供不同菜单操作。
	Saas化(系统租户管理员)：
	支持系统租户管理员对系统租户和系统菜单的管理功能。
	1）租户管理:
		①提供系统租户管理员对系统租户和系统菜单的创建与管理功能；
		②支持租户管理员对各租户状态进行设置与管理，租户增加租户过期时间及激活状态字段。通过过期时间控制租户的生效时间，到期后自动失效；通过激活状态可以随时控制租户的启用/禁用状态，启用状态的租户禁止删除操作；
		③支持租户管理员对各租户进行菜单权限的配置，实现各租户菜单的个性化设置。
	2）菜单管理：
		①支持租户管理员对系统菜单进行集中管理操作，提供菜单页、非菜单页及按钮多种菜单配置；
		②提供菜单列表支持树状层级显示，便于菜单维护与管理；
		③支持菜单权限码配置，可通过权限码控制用户对于不同菜单的访问权限。
	Saas化(普通租户/用户)：
	支持普通租户对角色、部门和用户的管理功能。
	1）角色管理：
		①支持角色数据租户间隔离，不同租户间可以创建同名角色，同一租户角色名唯一，租户数据互不干扰；
		②支持角色批量配置用户功能，轻松实现角色用户的批量配置；
		③支持角色菜单的重新分配，可以通过角色配置实现租户菜单角色级别的按需分配；
		④支持角色的数据权限配置，可以通过数据权限配置结合业务注解，实现角色的数据权限控制，数据权限支持五种级别可供选择：全部权限、自定义权限、部门权限、部门及以下权限、个人权限。
	2）部门管理
		①支持部门数据租户间隔离，不同租户部门数据互不干扰；
		②支持部门的批量用户配置，便于部门用户数据管理；
		③支持部门列表树状层级展示，部门结构一目了然；
		④支持部门模糊检索，轻松查找部门信息。
	3）用户管理
		①支持用户数据租户间隔离，不同租户用户数据互不干扰；
		②支持用户列表左侧部门树展示，方便部门用户的筛选；
		③支持在用户创建时对部门和角色权限的配置；
		④支持用户多条件模糊检索功能，可通过用户名、姓名、手机号、邮箱及个人描述进行用户检索。
```


```tex
2.定时任务
	①支持定时任务页面化配置，提供两种接入方式：全类名方式和bean注入方式；
	②支持cron表达式页面配置，提供两种方式：直接表达式输入和cron表达式生成器；
	③支持通过cron表达式生成器最近几次触发时间预览，方便用户调试使用；
	④支持错误执行策略配置，提供三种方式：立即执行、执行一次和放弃执行；
	⑤支持定时任务并发配置及启停状态配置；
	⑥支持定时任务列表导出功能。
```


```tex
3.消息队列管理
	①支持kafka消息队列的页面化配置管理；
	②支持kafka单节点和集群节点多集群配置管理；
	③支持kafka集群操作权限配置管理，通过操作权限控制生产topic和消费组group的操作权限；
	④支持kafka集群节点可视化，提供集群的host地址，端口及主备节点查看；
	⑤支持kafka集群的生产topic管理，提供topic的检索创建，topic详情(分区、副本、偏移量和消息数量)查看及各topic消费组详情(消费组列表，各消费组各分区消费偏移量及未消费消息数)查看功能；
	⑥支持kafka消费group管理，提供消费组的检索，group详情(订阅topic，topic各分区的消费偏移量及消息未消费条数)查看及topic删除功能；
	⑦支持kafka集群生产模拟功能，提供向kafka集群各topic进行单条数据或者多条数据分割发送的功能；
	⑧支持kafka集群消费模拟功能，提供对kafka集群各topic模拟消费功能，消息支持历史消息和最新消息，还支持对消息关键词高亮显示的功能。
```

**二、功能增强与优化:**

```tex
1.调整页面布局及样式，统一页面风格，操作按钮统一去除边框及背景，部分非菜单页替换为页面弹窗；
2.丰富页面主题，提供默认、雅蓝、暗黑、高亮四种主题可供用户选择；
3.对登录页面、logo重新设计，登录页增加租户信息字段；
4.调整后端代码结构，进行模块化拆分，方便用户集成及使用；
5.修复表单重复提交问题，提供防表单重复提交注解类，便于开发直接使用；
6.新用户登录修改密码后直接进入登录成功页面，避免再次登录；
7.去除机构管理及工作组管理，增加部门管理；去除异步路由，增加菜单管理；
8.修改系统security鉴权，token中增加租户相关信息；SecurityUtil工具类替换没SecurityHxUtils，可以通过该工具类直接获取当前用户租户信息。
```

**三、说明：**

> 默认关闭swagger文档接口及actuator健康检测接口，可在com.ctsi.ssdc.config.SecurityConfiguration#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)  中进行开启

```java
				.antMatchers("/v2/api-docs/**").permitAll()
				.antMatchers("/swagger-resources/configuration/ui").permitAll()
				.antMatchers("/swagger-ui/*").permitAll()
				// 接口鉴权
//				.antMatchers("/v2/api-docs/**").authenticated()
//				.antMatchers("/swagger-resources/configuration/ui").authenticated()
//				.antMatchers("/swagger-ui/*").authenticated()
//				.antMatchers("/swagger-ui.html").authenticated()
//
//				.antMatchers("/actuator").authenticated()
//				.antMatchers("/actuator/prometheus").authenticated()
```
比翼用户手册请参考：
http://10.1.160.67/udp_public/biyi/biyi-document/-/blob/master/比翼开发框架/比翼用户手册/后端代码使用手册.md
