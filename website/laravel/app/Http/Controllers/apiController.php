<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Task;
use Illuminate\Support\Facades\Auth;

class apiController extends Controller
{
    public function index()
    {
        $tasks = Task::all(); // find all the tasks , 
        return $tasks;
    }

    public function show($id)
    {
        $task = Task::find($id);
        return $task;           //檢索單筆資料 find single task
    }

    public function store(Request $request)
    {   
        $task = new Task;  //if we want to save new task, we must create new $task object
        $task->content = $request->input('content'); //save the value to the form which filed named task to the content column
        $task->user_id = $request->input('user_id'); // user_id  the user account id, which data belongs to the user we want to find
        $task->save();  
        
        return response()->json($task, 201);      //資料新增，回傳201代表資料成功新增
    }


    public function update($id, Request $request)
    {
        $task = Task::find($id);  // use PUT to update the data , Find which data we want to update
                                  // use id(database) to find which data we need to update 
        $task->user_id = $request->input('user_id'); // user_id  the user account id, which data belongs to the user we want to find
        $task->status = $request->input('status'); // 
        $task->content = $request->input('content');// the content, which is the content name
        $task->save();
        
        return response()->json($task, 200);   //資料更新，回傳200代表OK
    }

    public function delete($id)
    {
        $task = Task::find($id);
        $task->delete();
        return response()->json(null, 204);    //資料刪除，回傳204代表動作成功執行不回傳內容
    }
}
