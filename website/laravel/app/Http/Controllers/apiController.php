<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Task;
use Illuminate\Support\Facades\Auth;

class apiController extends Controller
{
    public function index()
    {
        $tasks = Task::all(); // find all the tasks
        return $tasks;
    }

    public function show($id)
    {
        $task = Task::find($id);
        return $task;           //檢索單筆資料 find single task
    }

    public function store(Request $request)
    {
        if($request->input('task'))
        {
            $task = new Task;  //if we want to save new task, we must create new $task object
            $task->content = $request->input('task'); //save the value to the form which filed named task to the content column
            Auth::user()->tasks()->save($task);   
        }
        return response()->json($task, 201);      //資料新增，回傳201代表資料成功新增
    }



    public function update($id, Request $request)
    {
        if($request->input('task'))
        {
            $task = Task::find($id);
            $task->content = $request->input('task');
            $task->save();
        }
        return response()->json($task, 200);   //資料更新，回傳200代表OK
    }

    public function delete($id)
    {
        $task = Task::find($id);
        $task->delete();
        return response()->json(null, 204);    //資料刪除，回傳204代表動作成功執行不回傳內容
    }
}
