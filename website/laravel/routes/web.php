<?php

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::get('/', function () {
    return view('home');
});

Auth::routes();

Route::get('/', 'HomeController@index')->name('home'); //??? difference betwwen first one and second one

Route::resource('files', 'FileController');

Route::resource('folders','FolderController');

Route::post('file/upload', 'FileController@store')->name('file.upload');

Route::post('upload', 'FileController@upload')->name('upload');

//2019-2-28
Route::get('file/download/{id}', 'FileController@download')->name('file.download');