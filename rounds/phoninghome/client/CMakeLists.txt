cmake_minimum_required(VERSION 3.25)

project(PhoningHomeClient
    VERSION 0.1
    DESCRIPTION "Client for the Phoning Home task at the Merlin Hunt"
    LANGUAGES CXX)

find_package(Boost 1.80 REQUIRED COMPONENTS system)

add_executable(client src/main.cpp)
target_link_libraries(client Boost::system)
