<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\File;
use App\Upload;
use Illuminate\Support\Facades\Auth;
use App\Http\Controllers\Controller;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Storage;

class FileController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function __construct()
    {
        $this->middleware('auth');
    }

    public function index()
    {
        $files = Auth::user()->files()->latest()->paginate(5);
        return view('files.index',compact('files'));
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        return view('files.create');
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
       $request->validate([
        'title' => 'required:max:255',
        'overview' => 'required',
        'price' => 'required|numeric'
      ]);

      auth()->user()->files()->create([
        'title' => $request->get('title'),
        'overview' => $request->get('overview'),
        'price' => $request->get('price')
      ]);

      return back()->with('message', 'Your file is submitted Successfully');
    }      

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        //
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */

     //2019-3-1
    public function edit($id)
    {
      $file = Auth::user()->files()->find($id);

      return view('files.edit',compact('file'));
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */

     //2019-3-1
    public function update(Request $request, $id)
    {

    
      $request->validate([
        'title' => 'required:max:255',
        'overview' => 'required',
        'price' => 'required|numeric'
      ]);

    
      //$update_file = Auth::user()->files()->find($id);
      //var_dump($file);

      Auth::user()->files()->find($id)->update($request->all());
        return redirect()->route('files.index')
                        ->with('success','File updated successfully');
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */

     //2019-3-1
    public function destroy($id)
    {
        //201931
        // $disk = Storage::disk('local');
        $filename = Auth::user()->uploads()->find($id)->filename;
        Storage::deleteDirectory('/files/'.$filename);

        Auth::user()->files()->find($id)->delete();
        DB::table('files')->where('id' , '=' , $id)->delete();
        return redirect()->route('files.index')
                        ->with('success','File deleted successfully');  
    }

    //2019-2-28
    public function download(Request $request, $id)
    { 
      $download_file = Auth::user()->uploads()->find($id);
      $download_filename = $download_file->filename;
      return response()->download(storage_path('app/files/files/'.$download_filename.'/'.$download_filename));

    }

    public function upload(Request $request)
    {
      $uploadedFile = $request->file('file');
      $filename = date("Y-m-d H.i.s ").'.'.$uploadedFile->getClientOriginalName();

       Storage::disk('local')->putFileAs(
        'files/'.$filename,
        $uploadedFile,
        $filename
      );

      $upload = new Upload;
      $upload->filename = $filename;

      $upload->user()->associate(auth()->user());

      $upload->save();

      return response()->json([
        'id' => $upload->id
      ]);
    }
}
