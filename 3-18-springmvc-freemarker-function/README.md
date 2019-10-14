# FreeMarker 内置函数说明

## 1.lower_case/upper_case 大小写转换
    "leon"?upper_case
    
## 2.cap_first 首字母大写
    "leon"?cap_first
    
## 3.index_of 查找字符索引
    "leon"?index_of("o")
    
## 4.返回字符串长度
    "leon"?length
    
## 5.round/floor/ceiling 四舍五入/下取整/上取整
    pi?floor
    
## 6.size 获取集合元素总数
    phones?size
    
## 7.first/last 获取第一个/最后一个元素
    phones?first
    
## 8.sort_by 按照某个属性对集合进行排序
    phones?sort_by("CPU")
    