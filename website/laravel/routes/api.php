<?php

use Illuminate\Http\Request;
use App\Task;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:api')->get('/user', function (Request $request) {
    return $request->user();
});

//apiController＠後面都有對應的function
Route::get('api','apiController@index');
Route::get('api/{id}','apiController@show');
Route::post('api','apiController@store');
Route::put('api/{id}','apiController@update');
Route::delete('api/{id}','apiController@delete');