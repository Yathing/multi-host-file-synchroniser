@extends('layouts.nav')

@section('content')

   <br>
   <h2 style="display:inline;">優惠券管理</h2>
   <a class="btn btn-info" style="float:right" href="{{ route('coupons.create') }}"> 創建新的優惠券</a>
   <hr>

    @if ($message = Session::get('success'))
        <div class="alert alert-success">
            <p>{{ $message }}</p>
        </div>
    @endif

    <!-- <div style="padding:0px 100px 0px 100px;"> -->

    <div class="row">
                    <div class="col-sm-12">
                        <div class="white-box">

                            <h3 class="box-title">折價卷
                            </h3>
                            <div class="table-responsive">
                                <table class="table ">
                                    <thead>
                                        <tr>
                                            <th>折價卷內容</th>
                                            <th>數量</th>
                                            <th>發行期限</th>
                                            <th>發行時間</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    @foreach ($coupons as $coupon)
                                        <tr>
                                            <td class="txt-oflo">{{ $coupon->content}}</td>
                                            <td>{{ $coupon->amount}}</td>
                                            <td class="txt-oflo">{{ $coupon->time}}</td>
                                            <td><span class="text-success">{{ $coupon->created_at}}</span></td>
                                            <td>

                                            <a class="btn btn-primary" href="{{ route('coupons.show',$coupon->id) }}">查看</a>
                                            <!-- <a class="btn btn-primary" href="{{ route('coupons.edit',$coupon->id) }}">Edit</a> -->
                                            {!! Form::open(['method' => 'DELETE','route' => ['coupons.destroy', $coupon->id],'style'=>'display:inline']) !!}
                                            {!! Form::submit('刪除', ['class' => 'btn btn-danger']) !!}
                                            {!! Form::close() !!}

                                            </td>
                                        </tr>
                                    </tbody>
                                    @endforeach
                                </table> 
                            </div>
                        </div>
                    </div>
        </div>
                <!-- /.row -->

    {!! $coupons->links() !!}
@endsection