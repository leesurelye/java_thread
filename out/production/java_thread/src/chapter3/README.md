# Chapter 3. Guarded Suspension模式
当你在家里换衣服时候，门铃突然响了，原来是快递员来送邮件了，这时，因为正在换衣服不能出去，所以只能先让快递员等待以下，换好衣服后，
才开门。

本章将学习Guarded Suspension模式，如果执行现在的处理会照成问题，就让执行处理的线程进行等待。这就是`Guarded Suspension模式`

Guarded Suspension模式通过让线程等待来保证实列的安全性，Guarded Suspension模式还有`guarded wait`, `spin lock`等称呼，在本章中都会
进行介绍