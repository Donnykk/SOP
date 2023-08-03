# cmake和CMakeLists.txt教程
# https://www.jianshu.com/p/cb4f8136a265
cmake_minimum_required(VERSION 3.16)
project(sdk_cxx)

set(CMAKE_CXX_STANDARD 14)
set(LIB_SRC
        common/OpenClient.cpp
        common/OpenClient.h
        common/RequestType.h
        common/tool.h
        common/sign.h
        common/RSASign.h
        common/RSASign.cpp
        common/sha256.hpp
        request/BaseRequest.h
        request/MemberInfoGetRequest.hpp
        thirdparty/base64/base64.h
        thirdparty/base64/base64.cpp
        common/sign.cpp common/tool.cpp common/sha256.cpp
        request/BaseRequest.cpp)

# openssl安装路径
set(OPENSSL_INC_DIR /usr/local/opt/openssl/include)
set(OPENSSL_LINK_DIR /usr/local/opt/openssl/lib)

include_directories(${OPENSSL_INC_DIR})
link_directories(${OPENSSL_LINK_DIR})
link_libraries(ssl crypto)

# 添加类库
add_library(lib ${LIB_SRC})
# jsoncpp
add_library(jsoncpp SHARED IMPORTED)
set_target_properties(jsoncpp PROPERTIES
        IMPORTED_LOCATION "/usr/local/lib/libjsoncpp.dylib"
        INTERFACE_INCLUDE_DIRECTORIES "/usr/local/include/json"
        )

# 添加可执行文件
add_executable(sdk_cxx main.cpp)

# 可执行文件依赖lib库
target_link_libraries(sdk_cxx lib ssl)
target_link_libraries(sdk_cxx jsoncpp)