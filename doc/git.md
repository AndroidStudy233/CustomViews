# 记录一些git的操作

## pull request
1. 创建特性分支
`git checkout -b work master`(其中work为你新建的特性分支，master为你当前坐在的分支)创建新的特性分支并自动切换

2. 提交修改
- `git diff` 查看修改的内容是否正确
- `git add readMe.md`（其中readMe.md为要添加的文件）向仓库中添加文件
- 执行命令：git commit -m “add readMe.md文件”提交说明

3. 创建远程分支
执行命令：git push origin work1(其中origin为当时fork的远程主分支的名称，一般默认为origin,work1为本地工作的特性分支)，
要从github发送pull request，github端的仓库中必须有一个包含了修改后的代码的分支，
所以需要创建一个与刚刚创建的特性分支（修改所在的分支）相对应的远程分支，
然后执行：git branch -a进行查看是否创建成功

4. 发送pull request
进入到自己的github账户下，并切换到创建的特性分支下，然后点击create pull request后，确定没问题，填写相关内容，然后点击send pull request