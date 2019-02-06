<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Task;
use Illuminate\Support\Facades\Auth;

class ToDoController extends Controller
{
    public function index()
    {
        $tasks = Task :: all();
        return view('index',compact('tasks'));
    }

    public function store(Request $request) //method for saving new task
    {
        if($request->input('task'))
        {
            $task = new Task;  //if we want to save new task, we must create new $task object
            $task->content = $request->input('task'); //save the value to the form which filed named task to the content column
            Auth::user()->tasks()->save($task);
        }
        return redirect()->back();
    }

    public function edit($id)
    {
        $task = Task::find($id);
        return view('edit',['task'=>$task]);
    }


    public function update($id, Request $request)
    {
        if($request->input('task'))
        {
            $task = Task::find($id);
            $task->content = $request->input('task');
            $task->save();
        }
        return redirect('/');// redirect to the main page 
    }

    public function delete($id)
    {
        $task = Task::find($id);
        $task->delete();
        return redirect()->back();
    }
}
