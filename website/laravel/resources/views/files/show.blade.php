@extends('layouts.nav')

@section('content')
   <br>
   <h2 style="display:inline;">查看優惠券內容</h2>
   <a class="btn btn-info" style="float:right" href="{{ route('coupons.index') }}"> 後退</a>
   <hr>


<div class="box" style="width:400px;">

    <ul class="alt">
		<li>
            <div class="row">
                <div class="col-xs-6 col-sm-6 col-md-6">
                    <div class="form-group">
                        <strong>優惠券內容:</strong>
                            {{ $coupon->content}}
                    </div>
                </div>  
            </div>
        </li>
		<li>            
            <div class="row">
                <div class="col-xs-12 col-sm-12 col-md-12">
                    <div class="form-group">
                        <strong>數量:</strong>
                            {{ $coupon->amount}}
                    </div>
                </div>  
            </div>
        </li>
		<li>
            <div class="row">
                <div class="col-xs-12 col-sm-12 col-md-12">
                    <div class="form-group">
                        <strong>發行期限:</strong>
                            {{ $coupon->time}}
                    </div>
                </div>  
            </div>
        </li>
	</ul>
</div>

@endsection